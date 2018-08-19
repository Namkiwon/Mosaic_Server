package com.angointeam.mosaic.api;

import com.angointeam.mosaic.api.response.BaseResponse;
import com.angointeam.mosaic.domain.Member;
import com.angointeam.mosaic.domain.Script;
import com.angointeam.mosaic.etc.S3Uploader;
import com.angointeam.mosaic.service.scripts.ScriptsService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/apis")
public class ScriptsController {

    @Autowired
    ScriptsService scriptsService;

    //스크립트 하나 ㅏ가져오기
    @GetMapping("/script")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization : Bearer {token}", required = true
                    , dataType = "string", paramType = "header")
    })
    public BaseResponse<Script> getScript(String scriptUuid){
        Script script = scriptsService.getScriptByUuid(scriptUuid);
        return responseScriptReturnSuccess(script);
    }

    //컨텐트 기반으로 검색된 스크립트 리스트 가져오기
    @GetMapping("/scripts/search")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization : Bearer {token}", required = true
                    , dataType = "string", paramType = "header")
    })
    public BaseResponse<List<Script>> searchByKeyword(@AuthenticationPrincipal @ApiIgnore final Member member,String keyword) {
        List<Script> scriptList = scriptsService.findAllByKeyword(keyword,member.getUuid());
        return responseScriptListReturnSuccess(scriptList);
    }

    @GetMapping("/scripts/mine")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization : Bearer {token}", required = true
                    , dataType = "string", paramType = "header")
    })
    public BaseResponse<List<Script>> getMyScripts(@AuthenticationPrincipal @ApiIgnore final Member member) {
        List<Script> scriptList = scriptsService.getMyScripts(member.getUuid());
        return responseScriptListReturnSuccess(scriptList);
    }

    //모든 스크립트 리스트 가져오기 (카테고리화 정리)
    @GetMapping("/scripts")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization : Bearer {token}", required = true
                    , dataType = "string", paramType = "header")
    })
    public BaseResponse<List<Script>> getAll(@AuthenticationPrincipal @ApiIgnore final Member member,@RequestParam(value = "categories", required = false) List<String> categories) {
        List<Script> scriptList = scriptsService.getAllScripts(member.getUuid(),categories);
        return responseScriptListReturnSuccess(scriptList);
    }

    //스크립트 작성하기
    @PostMapping("/script")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization : Bearer {token}", required = true
                    , dataType = "string", paramType = "header")
    })
    @ResponseBody
    public BaseResponse<Script> addScript(@AuthenticationPrincipal @ApiIgnore final Member member, String content , String categoryUuid, @RequestParam(value = "imgUrls", required = false) List<MultipartFile> multipartFiles) throws IOException {

        Script script = scriptsService.addScript(content,categoryUuid,member.getUuid(),multipartFiles);
        return responseScriptReturnSuccess(script);
    }

    //스크립트 지우기
    @DeleteMapping("/script")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization : Bearer {token}", required = true
                    , dataType = "string", paramType = "header")
    })
    public BaseResponse<String> deleteScript(String scriptUuid) throws IOException {
        scriptsService.deleteScript(scriptUuid);
        return responseMessageReturnSuccess("delete success");
    }

    private BaseResponse<String> responseMessageReturnSuccess(String resultMessage) {

        BaseResponse<String> result = new BaseResponse<>();
        result.setResponseCode(0);
        result.setHttpStatus(200);
        result.setMessage(resultMessage);

        return result;
    }

    private BaseResponse<Script> responseScriptReturnSuccess(Script script) {

        BaseResponse<Script> result = new BaseResponse<>();
        result.setResponseCode(0);
        result.setHttpStatus(200);
        result.setResult(script);

        return result;
    }

    private BaseResponse<List<Script>> responseScriptListReturnSuccess(List<Script> scriptList) {

        BaseResponse<List<Script>> result = new BaseResponse<>();
        result.setResponseCode(0);
        result.setHttpStatus(200);
        result.setResult(scriptList);

        return result;
    }

}
