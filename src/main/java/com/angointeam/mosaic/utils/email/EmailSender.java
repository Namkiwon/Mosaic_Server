package com.angointeam.mosaic.utils.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    public EmailSender(){
    }

    public void send(SimpleMailMessage message) {
        javaMailSender.send(message);
    }

    public static AuthEmailBuilder messageBuilder() {
        return new AuthEmailBuilder();
    }

}
