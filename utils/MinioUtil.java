package com.example.system.utils;

import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class MinioUtil {
    private final MinioClient minioClient;

    public MinioUtil(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    /**
     * 上传一个文件
     */

    public void uploadFile(InputStream stream, String bucket, String objectName) throws Exception {
        minioClient.putObject(PutObjectArgs.builder().bucket(bucket).object(objectName)
                .stream(stream, -1, 10485760).build());
    }

    /**
     * 删除一个对象
     */
    public void deleteObject(String bucket, String objectName) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucket).object(objectName).build());
    }

    /**
     * 清空bucket
     */
    public void clearBucket(String bucket) {
        try {
            if(minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())) {
                Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucket).build());
                for (Result<Item> result : results) {
                    deleteObject(bucket,result.get().objectName());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}