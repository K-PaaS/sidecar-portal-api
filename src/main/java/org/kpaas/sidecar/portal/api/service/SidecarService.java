package org.kpaas.sidecar.portal.api.service;

import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.messages.Item;
import org.container.platform.api.common.model.ResultStatus;
import org.kpaas.sidecar.portal.api.common.Common;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class SidecarService extends Common {
    public Object minioUploadFile(MultipartFile file) throws IOException, NoSuchAlgorithmException, InvalidKeyException {

        String serverUrl = afMinioUrl;
        String accessKey = afMinioAccessKey;
        String secretKey = afMinioSecretkey;
        String bucketName = afMinioBucket;
        String fileName =file.getOriginalFilename();


        ResultStatus resultStatus = new ResultStatus();
        resultStatus.setResultMessage("");
        resultStatus.setHttpStatusCode(200);
        resultStatus.setDetailMessage("");
        resultStatus.setNextActionUrl("");

        String filePath = System.getProperty("user.home") + "/" + fileName;

        System.out.println(filePath);
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.write(file.getBytes());
        fos.close();

        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(serverUrl)
                    .credentials(accessKey, secretKey)
                    .build();

            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .filename(filePath)
                            .build());

            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(bucketName)
                            .build()
            );
            resultStatus.setResultCode("SUCCESS");

            File localFile = new File(filePath);
            if (localFile.exists()) {
                localFile.delete();
            }
        } catch (
                MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
        }
        return resultStatus;
    }


    public Object minioDownloadFile() throws IOException, NoSuchAlgorithmException, InvalidKeyException {

        String serverUrl = afMinioUrl;
        String accessKey = afMinioAccessKey;
        String secretKey = afMinioSecretkey;
        String bucketName = afMinioBucket;
        //String fileName =file.getOriginalFilename();


        ResultStatus resultStatus = new ResultStatus();
        resultStatus.setResultMessage("");
        resultStatus.setHttpStatusCode(200);
        resultStatus.setDetailMessage("");
        resultStatus.setNextActionUrl("");

        //String filePath = System.getProperty("user.home") + "/" + fileName;

        //System.out.println(filePath);
        //FileOutputStream fos = new FileOutputStream(filePath);
        //fos.write(file.getBytes());
        //fos.close();

        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(serverUrl)
                    .credentials(accessKey, secretKey)
                    .build();

            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            /*minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .filename(filePath)
                            .build());*/
            minioClient.downloadObject(
                    DownloadObjectArgs.builder()
                            .bucket(bucketName)
                            .object("3.png")
                            .filename("C:\\Users\\이노그리드/3.png")
                            .build());

            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(bucketName)
                            .build()
            );
            resultStatus.setResultCode("SUCCESS");

            //File localFile = new File(filePath);
            //if (localFile.exists()) {
            //    localFile.delete();
            //}
        } catch (
                MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
        }
        return resultStatus;
    }
}
