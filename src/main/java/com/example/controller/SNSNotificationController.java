package com.example.controller;

import com.example.services.ISNSNotificationService;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("${controller.SNSNotification}")
public class SNSNotificationController {

    private static final Logger LOG = LoggerFactory.getLogger(SNSNotificationController.class);
    private final ISNSNotificationService snsNotificationService;

    @Value("${messages.controller.methodStart}")
    private String msgMethodStart;

    public SNSNotificationController(ISNSNotificationService snsNotificationService) {
        this.snsNotificationService = snsNotificationService;
    }

    @Post("${controller.SNSNotificationSMS}")
    public void smsNotification(String phoneNumber) {
        LOG.info(msgMethodStart);
        snsNotificationService.smsNotification(phoneNumber);
    }

    @Post("${controller.SNSNotificationEmail}")
    public void emailNotification(String email) {
        LOG.info(msgMethodStart);
        snsNotificationService.emailNotification(email);
    }
}
