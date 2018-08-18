package com.angointeam.mosaic.service.reply;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ScrpitNotFoundException extends RuntimeException {
    public ScrpitNotFoundException(){
        super("작성된 글을 찾을 수가 없습니다.");
    }
}
