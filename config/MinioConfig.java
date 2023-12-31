package com.example.system.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
	
	@Value("${minio.host}")
    private String host;
    @Value("${minio.accessKey}")
    private String accessKey;
    @Value("${minio.secretKey}")
    private String secretKey;
    
    @Bean
    public MinioClient getMinioClient() {
        return MinioClient.builder()
                .endpoint(host)
				.credentials(accessKey, secretKey)
                .build();
    }
    
}