package com.angointeam.mosaic.api;

import com.angointeam.mosaic.api.response.BaseResponse;
import com.angointeam.mosaic.domain.Member;
import com.angointeam.mosaic.domain.Script;
import com.angointeam.mosaic.domain.University;
import com.angointeam.mosaic.service.univ.UnivService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.List;

@RestController
public class UnivController {
    @Autowired
    UnivService univService;

    //스크립트 작성하기
    @PostMapping("/univ")
    @ResponseBody
    public BaseResponse<University> addScript(String name , String domain, @RequestParam(value = "imgUrl", required = false) MultipartFile multipartFile) throws IOException {
        University university = univService.saveUniv(name,domain,multipartFile);
        return responseUniversityReturnSuccess(university);
    }


    private BaseResponse<University> responseUniversityReturnSuccess(University university) {

        BaseResponse<University> result = new BaseResponse<>();
        result.setResponseCode(0);
        result.setHttpStatus(200);
        result.setResult(university);

        return result;
    }
}
