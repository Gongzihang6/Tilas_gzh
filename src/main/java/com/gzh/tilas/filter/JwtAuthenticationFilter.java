// filter/JwtAuthenticationFilter.java
package com.gzh.tilas.filter;

import com.gzh.tilas.pojo.Result;
import com.gzh.tilas.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader("Authorization");

        // 如果是登录请求或没有Token，直接放行
        if (request.getRequestURI().equals("/login") || jwt == null || !jwt.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = jwt.substring(7);

        Claims claims;
        try {
            claims = jwtUtil.parseToken(token);
        } catch (Exception e) {
            log.warn("JWT解析失败: {}", e.getMessage());
            // 返回JSON格式的错误信息
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(Result.error("令牌无效或已过期")));
            return;
        }

        // --- 【核心修改：将用户信息存入SecurityContext】 ---
        // 1. 从Claims中获取用户名
        String username = claims.get("username", String.class);

        // 2. 创建一个“已认证”的 Authentication 对象
        //    - principal: 当事人，这里是用户名
        //    - credentials: 凭证，对于已认证的用户，通常设为 null
        //    - authorities: 权限列表，我们之前没设置，这里先给一个空列表
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());

        // 3. 将 Authentication 对象设置到 Spring Security 的上下文中
        //    这一步至关重要！它告诉了整个框架，当前请求的用户是谁。
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // ----------------------------------------------------

        // 放行请求，让它进入后续的过滤器和Controller
        filterChain.doFilter(request, response);
    }
}