package com.angointeam.mosaic.api;


import com.angointeam.mosaic.api.response.BaseResponse;
import com.angointeam.mosaic.domain.University;
import com.angointeam.mosaic.service.metaService.MetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/meta")
public class MetaController {

    @Autowired
    private MetaService metaService;

    @GetMapping("/universities")
    BaseResponse<List<University>> getUniversities() {
        return responseUniversityReturnSuccess(metaService.findUniversities());
    }

    private BaseResponse<List<University>> responseUniversityReturnSuccess(List<University> universities) {

        BaseResponse<List<University>> result = new BaseResponse<>();
        result.setResponseCode(0);
        result.setHttpStatus(200);
        result.setResult(universities);

        return result;

    }
}
