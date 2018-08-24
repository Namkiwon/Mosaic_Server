package com.angointeam.mosaic.utils.email;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EmailSender {

    @Value("{appHost}")
    static String appHost;

    @Value("{appPort}")
    static String appPort;


    static final String FROM = "angointeam@gmail.com";
    static final String SUBJECT = "대학생 익명 커뮤니티 모자이크 가입 인증 메일";


    public void send(String to, String uuid, String emailKey) throws Exception {

        String html = "<h1>모자이크 가입 인증 메일</h1>"
                + "<p>다음 링크를 누르시면 가입인증을 완료합니다. <a href='"+appHost+":"+appPort
                + "/login/email/"+uuid+"/"+emailKey+"'>인증하기</a>";

        String text = "다음 링크를 누르시면 가입 인증을 완료 합니다. "
                + appHost + ":" + appPort
                + "/login/email/"+uuid+"/"+emailKey;

        System.out.println("send email with html : " + html);
        System.out.println("send email with text : " + text);


        AmazonSimpleEmailService client =
                AmazonSimpleEmailServiceClientBuilder.standard()
                        .withRegion(Regions.US_WEST_2).build();
        SendEmailRequest request = new SendEmailRequest()
                .withDestination(
                        new Destination().withToAddresses(to))
                .withMessage(new Message()
                        .withBody(new Body()
                                .withHtml(new Content()
                                        .withCharset("UTF-8").withData(html))
                                .withText(new Content()
                                        .withCharset("UTF-8").withData(text)))
                        .withSubject(new Content()
                                .withCharset("UTF-8").withData(SUBJECT)))
                .withSource(FROM);
        client.sendEmail(request);

    }

}
