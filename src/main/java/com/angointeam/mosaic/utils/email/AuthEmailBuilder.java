package com.angointeam.mosaic.utils.email;

import org.springframework.mail.SimpleMailMessage;

public class AuthEmailBuilder {

    private String sendTo;
    private String emailKey;

    public AuthEmailBuilder setSendTo(String sendTo) {
        this.sendTo = sendTo;
        return this;
    }

    public AuthEmailBuilder setEmailKey(String emailKey) {
        this.emailKey = emailKey;
        return this;
    }

    public SimpleMailMessage build() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(sendTo);
        message.setSubject("Mosaic 이메일 인증");
        message.setText(emailKey);
        return message;
    }
}
