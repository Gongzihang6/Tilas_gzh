package com.gzh.tilas.config;

import com.gzh.tilas.Service.impl.LoginServiceImpl;
import com.gzh.tilas.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // --- 核心配置：禁用CSRF，并放行所有请求 ---
        http
                // 禁用 CSRF
                .csrf(csrf -> csrf.disable())

                // 启用 CORS，它会使用你在 WebConfig 中定义的全局配置
                .cors(withDefaults())

                // 配置授权规则
                .authorizeHttpRequests(authz -> authz
                        // 放行所有 OPTIONS 类型的预检请求
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // 放行登录接口
                        .requestMatchers("/login").permitAll()

                        // 2. 在开发阶段，为了方便，暂时放行所有其他请求
                        //    这条规则应放在 OPTIONS 规则之后
                        // .anyRequest().permitAll()

                        // 除了登录接口，其他接口都需要认证
                        .anyRequest().authenticated()
                )
                // 【重要】将JWT过滤器添加到Spring Security的过滤器链中
                // 在UsernamePasswordAuthenticationFilter之前添加
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                // 禁用默认的Session管理，因为我们用JWT，是无状态的
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
