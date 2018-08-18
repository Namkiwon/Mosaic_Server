package com.angointeam.mosaic.api;

import com.angointeam.mosaic.api.response.BaseResponse;
import com.angointeam.mosaic.domain.Member;
import com.angointeam.mosaic.domain.University;
import com.angointeam.mosaic.service.member.MemberService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/apis")
public class MemberController {

    @Autowired
    private MemberService memberService;


    @GetMapping("/me")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization : Bearer {token}", required = true
                    , dataType = "string", paramType = "header")
    })
    BaseResponse<Member> me(@AuthenticationPrincipal @ApiIgnore final Member member) {
        return responseMemberReturnSuccess(member);
    }


    private BaseResponse<Member> responseMemberReturnSuccess(Member member) {

        BaseResponse<Member> result = new BaseResponse<>();
        result.setResponseCode(0);
        result.setHttpStatus(200);
        result.setResult(member);

        return result;

    }




}
