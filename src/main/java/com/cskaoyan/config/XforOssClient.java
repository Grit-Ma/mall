package com.cskaoyan.config;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
@ConfigurationProperties(prefix = "myoss")
public class XforOssClient {
    private String bucket;
    private String endPoint;
    private String accessKey;
    private String secret;

    public XforOssClient() {
    }

    public XforOssClient(String bucket, String endPoint, String accessKey, String secret) {
        this.bucket = bucket;
        this.endPoint = endPoint;
        this.accessKey = accessKey;
        this.secret = secret;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String ossFileUpload(MultipartFile myfile) throws IOException {
        InputStream inputStream = myfile.getInputStream();
        long size = myfile.getSize();
        String contentType = myfile.getContentType();


		/*String bucket = "cskaoyan";
		String endpoint = "oss-cn-beijing.aliyuncs.com";
		String accessKey = "LTAI8EgTxlc4QQZr";
		String secret = "RBDvSGZOR8DaUJxlLrW4Ed46RVnAkR";*/

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(size);
        objectMetadata.setContentType(contentType);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, uuid, inputStream, objectMetadata);

        OSSClient ossClient = new OSSClient(endPoint, accessKey, secret);
        ossClient.putObject(putObjectRequest);

        return uuid;
    }
}
