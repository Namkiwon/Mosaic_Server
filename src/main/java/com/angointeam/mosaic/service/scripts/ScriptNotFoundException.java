package com.angointeam.mosaic.service.scripts;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ScriptNotFoundException extends RuntimeException{
    public ScriptNotFoundException(){
        super("해당 스크립트를 찾을 수 없습니다.");
    }
}
