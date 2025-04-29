package com.saad.sa.email;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "email")
public class EmailEntity {

    private String toEmail;
    private String fromEmail;
    private String subject;
    private String attachment;
    private String body;

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "EmailEntity{" +
                "toEmail='" + toEmail + '\'' +
                ", fromEmail='" + fromEmail + '\'' +
                ", subject='" + subject + '\'' +
                ", attachment='" + attachment + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
