package com.s3.presigner.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@Slf4j
public class AwsBeanConfig {

    @Autowired
    private Environment environment;

    @Bean
    public AmazonS3 s3client() {
        log.info("Environment is {} :: ", environment.getActiveProfiles());
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(environment.getProperty("s3.access.key.id"),
                environment.getProperty("s3.secret.access.key"));
        return AmazonS3ClientBuilder.standard()
                .withRegion("us-east-1")
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withClientConfiguration(new ClientConfiguration().withSignerOverride("AWSS3V4SignerType"))
                .build();
    }
}
