package com.aws.java.sdk.controller;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.aws.java.sdk.constants.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/aws")
@RestController
public class S3Controller {

    Constants constants = new Constants();
    AWSCredentials credentials = new BasicAWSCredentials(constants.AWS_ACCESS_KEY, constants.AWS_ACCESS_SEC);

    AmazonS3 s3 = AmazonS3ClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.US_EAST_2)
            .build();

    Bucket b = null;

  @GetMapping(value = "create")
    public String createBucket(@RequestParam(name = "bucketName",required = true) final String bucketName) {
        if (s3.doesBucketExistV2(bucketName)) {
            return "Bucket Exists!!!";
        } else {
            try {
                s3.createBucket(bucketName);
            } catch (IllegalArgumentException e) {
                return e.toString();
            }
            return "bucket created";
        }
    }

    @GetMapping(value = "delete")
    public String deleteBucket(@RequestParam(name = "bucketName",required = true) final String bucketName){
        if(!s3.doesBucketExistV2(bucketName)){
            return "BUcket Exists";
        } else {
            try {
                s3.deleteBucket(bucketName);
            }catch (Exception e){
                return e.toString();
            }
        }
        return "Bucket Deleted";
    }
}
