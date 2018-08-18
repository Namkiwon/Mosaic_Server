package com.angointeam.mosaic.service.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
public class EmailSendErrorException extends RuntimeException{
    public EmailSendErrorException(){
        super("인증 메일 보내기에 실패하였습니다.");
    }
}
