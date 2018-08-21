package com.angointeam.mosaic.service.scrap;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ScrapNotFoundException extends RuntimeException{
    public ScrapNotFoundException(){
        super("해당 스크랩을 찾을 수 없습니다.");
    }
}
