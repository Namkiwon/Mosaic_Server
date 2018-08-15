package com.angointeam.mosaic.api;

import com.angointeam.mosaic.api.response.BaseResponse;
import com.angointeam.mosaic.domain.Category;
import com.angointeam.mosaic.domain.Scrap;
import com.angointeam.mosaic.domain.Script;
import com.angointeam.mosaic.service.category.CategoryService;
import com.angointeam.mosaic.service.scrap.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ScrapController {

    @Autowired
    ScrapService scrapService;

    @GetMapping("/scrap")
    public Scrap getScrap(String scriptUuid) {
        return scrapService.getScrapByUuid(scriptUuid);
    }

//    @DeleteMapping("/scrap")
//    public BaseResponse<String> deleteScrap(String scriptUuid) {
//
//        return scrapService.getScrapByUuid(scriptUuid);
//    }


    @GetMapping("/scraps")
    public BaseResponse<List<Script>> getAll(String memberUuid) {
        List<Script> scrapList =scrapService.getAllScrpasByMemberUuid(memberUuid);
        return responseScriptListReturnSuccess(scrapList);
    }

    @PostMapping("/scrap")
    @ResponseBody
    public BaseResponse<Scrap> addScrap(String scriptUuid,String memberUuid) throws IOException {
        Scrap scrap =scrapService.addScrap(scriptUuid, memberUuid);
        return responseScrapReturnSuccess(scrap);
    }

    private BaseResponse<Scrap> responseScrapReturnSuccess(Scrap scrap) {

        BaseResponse<Scrap> result = new BaseResponse<>();
        result.setResponseCode(0);
        result.setHttpStatus(200);
        result.setResult(scrap);
        return result;
    }

    private BaseResponse<List<Script>> responseScriptListReturnSuccess(List<Script> scrapList) {
        BaseResponse<List<Script>> result = new BaseResponse<>();
        result.setResponseCode(0);
        result.setHttpStatus(200);
        result.setResult(scrapList);
        return result;
    }

//    private BaseResponse<String> responseDeleteScrapSuccess(String resultMessage) {
//
//        BaseResponse<String> result = new BaseResponse<>();
//        result.setResponseCode(0);
//        result.setHttpStatus(200);
//        result.setResult(resultMessage);
//        return result;
//    }

}
