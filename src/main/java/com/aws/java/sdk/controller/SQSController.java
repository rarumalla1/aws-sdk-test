package com.aws.java.sdk.controller;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import com.aws.java.sdk.constants.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("SQS")
@RestController
public class SQSController {

    Constants constants = new Constants();
    AWSCredentials credentials = new BasicAWSCredentials(constants.AWS_ACCESS_KEY, constants.AWS_ACCESS_SEC);

    AmazonSQS sqs = AmazonSQSClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.US_EAST_2)
            .build();
    String s = null;

    @GetMapping(value = "create")
    public String createQueue(@RequestParam(name = "qName", required = true) final String qName){

       try {
           GetQueueUrlResult sqsString = sqs.getQueueUrl(qName);
           if(sqsString.getQueueUrl().contains(qName)){
               s = "Existing Queue";
           }
       }catch (Exception e) {
           sqs.createQueue(qName);
           s = "Queue_Created";
       }
       return s;
    }

    @GetMapping(value = "delete")
    public String deleteQueue(@RequestParam(name = "qName", required = true)final String qName) {

        try {
            GetQueueUrlResult sqsString = sqs.getQueueUrl(qName);
            if(sqsString.getQueueUrl().contains(qName)){
                sqs.deleteQueue(qName);
                s =  "Queue_Deleted";
            }
        } catch (Exception e){
            s = "Queue_Does_Not_Exist";
        }
        return s;
    }

}
