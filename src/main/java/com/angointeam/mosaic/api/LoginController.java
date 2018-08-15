package com.angointeam.mosaic.api;

import com.angointeam.mosaic.api.response.BaseResponse;
import com.angointeam.mosaic.service.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/email")
    BaseResponse<String> loginEmail(@RequestParam("email") String email) {

        BaseResponse<String> response = new BaseResponse<>();
        response.setResult(loginService.authentication(email));

        return response;
    }

    @PostMapping("/token")
    BaseResponse<String> obtainToken(@RequestParam("uuid") String uuid, @RequestParam("authKey") String authKey) {

        BaseResponse<String> response = new BaseResponse<>();
        response.setResult(loginService.login(uuid,authKey));

        return response;
    }

}
