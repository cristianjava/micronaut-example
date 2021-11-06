package com.example.services.impl;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.example.services.ISNSNotificationService;
import io.micronaut.context.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class SNSNotificationServiceImpl implements ISNSNotificationService {

    private static final Logger LOG = LoggerFactory.getLogger(SNSNotificationServiceImpl.class);

    @Value("${messages.services.methodStart}")
    private String msgMethodStart;
    @Value("${messages.services.methodEnd}")
    private String msgMethodEnd;
    @Value("${aws.sns.accessKey}")
    private String accessKey;
    @Value("${aws.sns.secretId}")
    private String secretId;

    @Override
    public void smsNotification(String phoneNumber) {
        LOG.info(msgMethodStart);
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretId);
        AmazonSNS snsClient = AmazonSNSClient.builder()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .build();
        Map<String, MessageAttributeValue> smsAttributes = new HashMap<>();
        smsAttributes.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue().withStringValue("myWebsite")
                .withDataType("String"));
        smsAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue().withStringValue("Transactional")
                .withDataType("String"));
        PublishResult result = snsClient.publish(new PublishRequest()
                .withMessage("Message")
                .withPhoneNumber(phoneNumber)
                .withMessageAttributes(smsAttributes));
        LOG.info(msgMethodEnd);
    }

    @Override
    public void emailNotification(String email) {
        LOG.info(msgMethodStart);
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretId);
        AmazonSNS snsClient = AmazonSNSClient.builder()
                .withRegion(Regions.SA_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .build();

        // send email to all subscribers on a topicArn
        final String msg = "If you receive this message, publishing a message to an Amazon SNS topic works.";
        final PublishRequest publishRequest = new PublishRequest(
                "arn:aws:sns:sa-east-1:396003076446:MyWebsite",
                "message",
                "subject");
        final PublishResult publishResponse = snsClient.publish(publishRequest);

        // send message to one email for subscribed on a topicArn
        Map<String, MessageAttributeValue> smsAttributes = new HashMap<>();
        smsAttributes.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue().withStringValue("myWebsite")
                .withDataType("String"));
        final SubscribeRequest subscribeRequest = new SubscribeRequest(
                "arn:aws:sns:sa-east-1:396003076446:MyWebsite",
                "email",
                email);
        snsClient.subscribe(subscribeRequest);

        System.out.println("SubscribeRequest: " + snsClient.getCachedResponseMetadata(subscribeRequest));
        System.out.println("To confirm the subscription, check your email.");
    }
}
