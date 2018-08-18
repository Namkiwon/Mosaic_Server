package com.angointeam.mosaic.service.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
public class EmailAuthErrorException extends RuntimeException {
    public EmailAuthErrorException(){
        super("유효하지 않은 이메일 인증 링크 입니다.");
    }
}
