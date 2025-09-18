package com.gzh.tilas.Controller;



import com.gzh.tilas.pojo.LoginDTO;
import com.gzh.tilas.pojo.Result;
import com.gzh.tilas.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager; // 注入 AuthenticationManager
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO loginDTO) {
        try {
            // 1. 【核心】调用 AuthenticationManager 的 authenticate 方法进行认证
            //    它会自动调用 UserDetailsServiceImpl 的 loadUserByUsername 方法
            //    并使用 PasswordEncoder 比较密码
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // 2. 如果认证通过，authentication 对象中会包含用户信息
            User userDetails = (User) authentication.getPrincipal();
            Map<String, Object> claims = new HashMap<>();
            claims.put("username", userDetails.getUsername());

            String jwt = jwtUtil.generateToken(claims);
            return Result.success(jwt);
        }catch (AuthenticationException e){
            // 3. 如果认证失败（密码错误、用户不存在等），会抛出异常
            return Result.error("用户名或密码错误");
        }

    }
}
