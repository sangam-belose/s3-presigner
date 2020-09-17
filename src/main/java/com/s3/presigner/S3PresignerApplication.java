package com.s3.presigner;

import com.s3.presigner.service.UrlSigner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
public class S3PresignerApplication {

    @Autowired
    private UrlSigner urlSigner;

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(S3PresignerApplication.class);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return new S3UrlCommandLineRunner();
    }

    public class S3UrlCommandLineRunner implements CommandLineRunner {
        @Override
        public void run(String... args) throws Exception {
            runBackGroundProcess();
        }

        private void runBackGroundProcess() throws Exception {
            String url = urlSigner.generatePresignedUrl(environment.getProperty("bucketname"),
                    environment.getProperty("object.keyname"));
            log.info("url = {}", url);
        }

    }
}
