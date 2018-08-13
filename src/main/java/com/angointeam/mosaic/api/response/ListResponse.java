package com.angointeam.mosaic.api.response;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListResponse<T extends List> extends BaseResponse<T> {
    private int total;
}
