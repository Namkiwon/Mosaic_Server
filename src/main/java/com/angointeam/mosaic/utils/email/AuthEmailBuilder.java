package com.angointeam.mosaic.utils.email;

import com.angointeam.mosaic.config.security.EncryptionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import java.net.URLEncoder;

public class AuthEmailBuilder {

    private String sendTo;
    private String emailKey;
    private String uuid;

    @Value("{appHost}")
    private String serverPort = "8080";

    @Value("appPort")
    private String contextPath = "localhost";

    private static final String CONTENT = "다음 링크를 누르시면 인증이 완료 됩니다. : ";

    public AuthEmailBuilder setSendTo(String sendTo) {
        this.sendTo = sendTo;
        return this;
    }

    public AuthEmailBuilder setEmailKey(String emailKey) {
        this.emailKey = emailKey;
        return this;
    }

    public AuthEmailBuilder setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public SimpleMailMessage build() throws Exception{
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(sendTo);
        message.setSubject("Mosaic 이메일 인증");
        message.setText(CONTENT + createLink(emailKey));
        return message;
    }

    private String createLink(String key) throws Exception{
        return "http://"+ contextPath + ":" +serverPort + "/login/email/"+uuid+"/"+emailKey;
    }
}
