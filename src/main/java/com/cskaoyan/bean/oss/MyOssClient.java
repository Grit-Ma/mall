package com.cskaoyan.bean.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.cskaoyan.bean.sys.Storage;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@Component
@ConfigurationProperties(prefix = "myoss")
public class MyOssClient {
    String bucket;
    String endpoint;
    String accesskey;
    String secret;

    public MyOssClient(String bucket, String endpoint, String accesskey, String secret) {
        this.bucket = bucket;
        this.endpoint = endpoint;
        this.accesskey = accesskey;
        this.secret = secret;
    }

    public MyOssClient() {
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccesskey() {
        return accesskey;
    }

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Storage fileUpLoad(MultipartFile file) throws IOException {
        //获取需要上传的文件的信息
        String contentType = file.getContentType();
        long size = file.getSize();
        String originalFilename = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();

        //设置文件上传的大小和类型
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(contentType);
        objectMetadata.setContentLength(size);

        //生成一个随机的文件名
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket,uuid,inputStream,objectMetadata);

        //ossclient
        OSSClient ossClient = new OSSClient(endpoint, accesskey, secret);
        ossClient.putObject(putObjectRequest); //完成上传操作

        //返回值是一个存储信息的对象
        Storage storage = new Storage();
        storage.setSize(new Long(size).intValue());
        storage.setAddTime(new Date());
        storage.setUpdateTime(new Date());
        storage.setName(originalFilename);
        storage.setType(contentType);
        storage.setUrl("http://"+bucket+"."+endpoint+"/"+uuid);
        storage.setKey(uuid+originalFilename);
        storage.setDeleted(false);
        return storage;
    }

}
