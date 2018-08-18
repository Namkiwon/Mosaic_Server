package com.angointeam.mosaic.service.reply;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ReplyNotFoundException extends RuntimeException{
    public ReplyNotFoundException() {
        super("유효한 댓글을 찾을 수 없습니다.");
    }
}
