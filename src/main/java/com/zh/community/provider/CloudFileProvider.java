package com.zh.community.provider;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class CloudFileProvider {
    // 1 初始化用户身份信息（secretId, secretKey）。
    @Value("${qcloud.secret.id}")
    String secretId;
    @Value("${qcloud.secret.key}")
    String secretKey;
    @Value("${qcloud.path}")
    String path;
    @Value("${qcloud.bucket}")
    String bucketName;

    public String upload(File file,String fileName){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, secretId, secretKey);
        String key="images/"+fileName;
        PutObjectRequest putObjectRequest=new PutObjectRequest(bucketName, key, file);
        PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);
        // 关闭OSSClient。
        ossClient.shutdown();
        return path+key;
    }


}
