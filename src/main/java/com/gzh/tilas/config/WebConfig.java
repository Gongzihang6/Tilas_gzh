import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 对所有接口路径生效
                // 1. 设置允许的源，这里写你Vue项目的地址
                .allowedOrigins("http://localhost:5173")
                // 2. 允许的方法
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 3. 允许携带凭证（比如Cookie），如果你的登录依赖Session，这个必须是true
                .allowCredentials(true)
                // 4. 允许所有请求头
                .allowedHeaders("*")
                // 5. 暴露哪些响应头给前端JS访问，如果登录成功后Token在响应头里，需要配置
                // .exposedHeaders("Authorization") 
                // 6. 预检请求的有效期，单位秒
                .maxAge(3600);
    }
}