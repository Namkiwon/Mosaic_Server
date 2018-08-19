package com.angointeam.mosaic.api;

import com.angointeam.mosaic.api.response.BaseResponse;
import com.angointeam.mosaic.domain.Category;
import com.angointeam.mosaic.domain.Member;
import com.angointeam.mosaic.domain.Scrap;
import com.angointeam.mosaic.domain.Script;
import com.angointeam.mosaic.service.category.CategoryService;
import com.angointeam.mosaic.service.scrap.ScrapService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apis")
public class ScrapController {

    @Autowired
    ScrapService scrapService;

    @GetMapping("/scrap")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization : Bearer {token}", required = true
                    , dataType = "string", paramType = "header")
    })
    public Scrap getScrap(String scriptUuid) {
        return scrapService.getScrapByUuid(scriptUuid);
    }

    @DeleteMapping("/scrap")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization : Bearer {token}", required = true
                    , dataType = "string", paramType = "header")
    })
    public BaseResponse<String> deleteScrap(@AuthenticationPrincipal @ApiIgnore final Member member,String scriptUuid) {
        scrapService.deleteScrap(scriptUuid,member.getUuid());
        return responseMessageReturnSuccess("delete success");
    }


    @GetMapping("/scraps")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization : Bearer {token}", required = true
                    , dataType = "string", paramType = "header")
    })
    public BaseResponse<List<Script>> getAll(@AuthenticationPrincipal @ApiIgnore final Member member) {
        List<Script> scrapList =scrapService.getAllScrpasByMemberUuid(member.getUuid());
        return responseScriptListReturnSuccess(scrapList);
    }

    @PostMapping("/scrap")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization : Bearer {token}", required = true
                    , dataType = "string", paramType = "header")
    })
    @ResponseBody
    public BaseResponse<Script> addScrap(@AuthenticationPrincipal @ApiIgnore final Member member, String scriptUuid) throws IOException {
        Script script =scrapService.addScrap(scriptUuid, member.getUuid());
        return responseScrapReturnSuccess(script);
    }

    private BaseResponse<Script> responseScrapReturnSuccess(Script script) {

        BaseResponse<Script> result = new BaseResponse<>();
        result.setResponseCode(0);
        result.setHttpStatus(200);
        result.setResult(script);
        return result;
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

    private BaseResponse<String> responseMessageReturnSuccess(String resultMessage) {

        BaseResponse<String> result = new BaseResponse<>();
        result.setResponseCode(0);
        result.setHttpStatus(200);
        result.setResult(resultMessage);
        return result;
    }

}
