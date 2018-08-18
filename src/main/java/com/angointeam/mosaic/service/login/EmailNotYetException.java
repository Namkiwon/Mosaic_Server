package com.angointeam.mosaic.service.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NETWORK_AUTHENTICATION_REQUIRED)
public class EmailNotYetException extends RuntimeException{
    public EmailNotYetException(){
        super("아직 이메일 인증이 되지 않았습니다.");
    }
}
