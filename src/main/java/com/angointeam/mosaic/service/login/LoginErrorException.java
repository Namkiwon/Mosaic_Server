package com.angointeam.mosaic.service.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class LoginErrorException extends RuntimeException {
    public LoginErrorException() {
        super("로그인 중 에러가 발생했습니다. 다시 로그인 해주세요.");
    }
}
