package com.saad.sa.email;

import com.saad.sa.email.EmailService.EmailResp;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/emails")
public class EmailController {
    private static final Logger LOG = LoggerFactory.getLogger(EmailController.class);
    private EmailService emailService;
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }


    @PostMapping("/send")
    public EmailResp sendCustomEmail(@RequestBody @Valid EmailEntity emailRequest) throws MessagingException {
        try {
            var result = emailService.sendEmail(emailRequest);
            LOG.info("EmailController.sendCustomEmail({}) => {}", emailRequest, result);
            return result;
        }catch (Exception e){
            LOG.error("EmailController.sendCustomEmail({}) => Error!!!!", emailRequest, e);
            throw e;
        }
    }

    @PostMapping("/send-from-config")
    public EmailResp sendEmailFromConfig() throws MessagingException {
        try {
            var result = emailService.sendEmailFromYaml();
            LOG.info("EmailController.sendCustomEmail() => {}", result);
            return result;
        } catch (Exception e) {
            LOG.error("EmailController.sendCustomEmail() => Error!!!!", e);
            throw e;
        }
    }
}
