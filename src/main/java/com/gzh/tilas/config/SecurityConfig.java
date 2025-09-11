package com.gzh.tilas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
                        // 【核心修改】
                        // 1. 无条件放行所有 OPTIONS 类型的预检请求
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // 2. 在开发阶段，为了方便，暂时放行所有其他请求
                        //    这条规则应放在 OPTIONS 规则之后
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}
