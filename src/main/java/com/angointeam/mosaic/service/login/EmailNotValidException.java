package com.angointeam.mosaic.service.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EmailNotValidException extends RuntimeException {
    public EmailNotValidException() {
        super("유효한 대학의 이메일을 찾을 수 없습니다.");
    }
}
