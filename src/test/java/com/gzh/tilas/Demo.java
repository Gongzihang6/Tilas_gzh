package com.gzh.tilas;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import org.junit.jupiter.api.Test; // 导入JUnit 5的Test注解
import org.springframework.boot.test.context.SpringBootTest; // 加上这个，让它成为一个Spring Boot测试

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;

@SpringBootTest // 告诉Spring Boot这是一个测试类
public class Demo {

    @Test // 标记这是一个测试方法
    public void testUploadFileToOss() throws Exception { // 方法名可以自定义
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-beijing.aliyuncs.com"; // 您的Endpoint可能不包含bucket name
        // 从环境变量中获取访问凭证。
        EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
        // 填写Bucket名称
        String bucketName = "tilas-gzh";
        // 填写Object完整路径
        String objectName = "001.jpg";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);

        try {
            File file = new File("C:\\Users\\gzh\\Pictures\\Screenshots\\gzh.jpg");
            byte[] content = Files.readAllBytes(file.toPath());

            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content));

            System.out.println("文件上传成功！");

        } catch (OSSException oe) {
            System.out.println("OSS服务器拒绝了请求. Error Message:" + oe.getErrorMessage());
            // ... 其他错误日志 ...
        } catch (ClientException ce) {
            System.out.println("客户端发生错误，例如网络问题. Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}