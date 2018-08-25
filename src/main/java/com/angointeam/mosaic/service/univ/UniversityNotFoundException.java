package com.angointeam.mosaic.service.univ;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UniversityNotFoundException extends RuntimeException{
    public UniversityNotFoundException(){
        super("해당 대학을 찾을 수 없습니다.");
    }
}
