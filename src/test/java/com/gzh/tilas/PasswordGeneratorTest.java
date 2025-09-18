// 在 src/test/java/com/gzh/tilas/PasswordGeneratorTest.java

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGeneratorTest {

    @Test
    public void generatePassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // 假设你的明文密码是 "123456"
        String rawPassword = "123456";

        String encodedPassword = passwordEncoder.encode(rawPassword);

        // 打印出加密后的密码，这个才是你应该存到数据库里的
        System.out.println("BCrypt Encoded Password: " + encodedPassword);

        // 示例输出: BCrypt Encoded Password: $2a$10$yourGeneratedHashString...
    }
}