package com.angointeam.mosaic.api;

import com.angointeam.mosaic.api.response.BaseResponse;
import com.angointeam.mosaic.domain.Member;
import com.angointeam.mosaic.service.login.EmailAuthErrorException;
import com.angointeam.mosaic.service.login.EmailNotValidException;
import com.angointeam.mosaic.service.login.LoginService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/email")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "이메일 도메인에서 유효한 대학이 없음"),
            @ApiResponse(code = 417, message = "인증메일 보내기 실패"),
            @ApiResponse(code = 409, message = "왜 그런지 모르는 에러 발생")
    })
    BaseResponse<Map<String,String>> loginEmail(@RequestParam("email") String email) {

        BaseResponse<Map<String,String>> response = new BaseResponse<>();
        response.setResult(loginService.authentication(email));

        return response;
    }

    @PostMapping("/token")
    BaseResponse<Map<String,String>> obtainToken(@RequestParam("uuid") String uuid, @RequestParam("authKey") String authKey) {

        BaseResponse<Map<String,String>> response = new BaseResponse<>();
        response.setResult(loginService.login(uuid,authKey));

        return response;
    }

    @GetMapping("/auth/{uuid}/{emailKey}")
    String authEmail(@PathVariable String uuid, @PathVariable String emailKey) {
        System.out.println(uuid + " / " + emailKey);
        Member member = loginService.emailAuthentication(uuid, emailKey);
        return "인증이 완료 되었습니다. 앱으로 가서 확인을 눌러주세요.";
    }



}
