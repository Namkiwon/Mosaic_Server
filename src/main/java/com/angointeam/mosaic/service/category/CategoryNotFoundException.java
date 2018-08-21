package com.angointeam.mosaic.service.category;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException (){
        super("해당 카테고리를 찾을 수 없습니다.");
    }
}
