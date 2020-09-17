package com.s3.presigner.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UrlSigner {
    @Autowired
    private AmazonS3 amazonS3;

    @Value("${presigned.url.expiration.seconds}")
    private Long expirationSeconds;

    public String generatePresignedUrl(String bucketName, String objectKey) {
        String url = null;
        try {
            java.util.Date expiration = new java.util.Date();
            long expTimeMillis = expiration.getTime();
            expTimeMillis += 1000 * expirationSeconds;
            expiration.setTime(expTimeMillis);

            // Generate the presigned URL.
            log.info("Generating pre-signed URL.");
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, objectKey)
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);
            url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest)
                    .toString();
            return url;
        } catch (AmazonServiceException e) {
            log.error("Error in creating pre-signed url: {}", e.getErrorMessage());
        } catch (SdkClientException e) {
            log.error("Error in creating pre-signed url: {}", e.getMessage());
        }
        return url;
    }
}
