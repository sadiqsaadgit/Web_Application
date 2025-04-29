package com.saad.sa.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@ConditionalOnBean(EmailConfig.class)
public class EmailService {

    private static final Logger LOG = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailConfig emailConfig;

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("pdf", "docx", "doc", "txt", "xlsx", "csv");

    public record EmailResp(String response){}

    public EmailResp sendEmail(EmailEntity request) throws MessagingException {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(request.getToEmail());
            helper.setSubject(request.getSubject());
            helper.setText(request.getBody());

            if (request.getAttachment() != null && !request.getAttachment().isEmpty()) {
                File folder = new File(request.getAttachment());
                if (folder.exists() && folder.isDirectory()) {
                    boolean attached = false;
                    for (File file : Objects.requireNonNull(folder.listFiles())) {
                        String extension = getFileExtension(file.getName());
                        if (file.isFile() && ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
                            helper.addAttachment(file.getName(), file);
                            LOG.info("Attached file: {}", file.getName());
                            attached = true;
                        }
                    }
                    if (!attached) {
                        LOG.warn("No valid attachment found in folder: {}", request.getAttachment());

                    }
                } else {
                    LOG.warn("No valid directory: {}",request.getAttachment());
                }
            }
            mailSender.send(message);
            LOG.debug("EmailService.sendEmail({}) => {}", request, message);
            return new EmailResp("Email Sent Successfully!!!!");
        } catch (Exception e) {
            LOG.error("EmailService.sendEmail({}) => ", request, e);
            throw e;
        }
    }

    public EmailResp sendEmailFromYaml() throws MessagingException {
        try {
            EmailEntity entity = new EmailEntity();
            entity.setFromEmail(emailConfig.getFrom());
            entity.setToEmail(emailConfig.getTo());
            entity.setSubject(emailConfig.getSubject());
            entity.setBody(emailConfig.getBody());
            entity.setAttachment(emailConfig.getAttachment());
            LOG.debug("EmailService.sendEmailFromYaml() => {}", entity);
            return sendEmail(entity);
        } catch (Exception e) {
            LOG.error("EmailService.sendEmailFromYaml() => ", e);
            throw e;
        }
    }

    private String getFileExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        return (lastIndex != -1) ? fileName.substring(lastIndex + 1) : "";
    }
}
