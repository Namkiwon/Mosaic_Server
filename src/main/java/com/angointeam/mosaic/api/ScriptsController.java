package com.angointeam.mosaic.api;

import com.angointeam.mosaic.api.response.BaseResponse;
import com.angointeam.mosaic.domain.Member;
import com.angointeam.mosaic.domain.Script;
import com.angointeam.mosaic.etc.S3Uploader;
import com.angointeam.mosaic.service.scripts.ScriptsService;
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
public class ScriptsController {

    @Autowired
    ScriptsService scriptsService;

    @GetMapping("/script")
    public BaseResponse<Script> getScript(String scriptUuid){
        Script script = scriptsService.getScriptByUuid(scriptUuid);
        return responseScriptReturnSuccess(script);
    }

    @GetMapping("/scripts")
    public BaseResponse<List<Script>> getAll(@AuthenticationPrincipal @ApiIgnore final Member member) {
        List<String> categories = new ArrayList<>();
        List<Script> scriptList = scriptsService.getAllScripts(member.getUuid(),categories);
        System.out.println(categories);
        return responseScriptListReturnSuccess(scriptList);
    }

    @PostMapping("/script")
    @ResponseBody
    public BaseResponse<Script> addScript(@AuthenticationPrincipal @ApiIgnore final Member member, String content , String categoryUuid, @RequestParam("imgUrls") List<MultipartFile> multipartFiles) throws IOException {
        Script script = scriptsService.addScript(content,categoryUuid,member.getUuid(),multipartFiles);
        return responseScriptReturnSuccess(script);
    }

    @DeleteMapping("/script")
    public BaseResponse<String> deleteScript(String scriptUuid) throws IOException {
        Script script = scriptsService.getScriptByUuid(scriptUuid);
        scriptsService.deleteScript(script);
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
