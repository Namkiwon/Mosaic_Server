package com.angointeam.mosaic.api;


import com.angointeam.mosaic.api.response.BaseResponse;
import com.angointeam.mosaic.domain.Member;
import com.angointeam.mosaic.domain.Reply;
import com.angointeam.mosaic.domain.Script;
import com.angointeam.mosaic.service.reply.ReplyService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/apis")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @GetMapping("/replies")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization : Bearer {token}", required = true
                    , dataType = "string", paramType = "header")
    })
    BaseResponse<List<Reply>> getReplies(@RequestParam("scriptUuid") String scriptUuid) {
        return responseRepliesReturnSuccess(replyService.getReplies(scriptUuid));
    }

    @PostMapping("/reply")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization : Bearer {token}", required = true
                    , dataType = "string", paramType = "header")
    })
    @ResponseBody
    public BaseResponse<Reply> addReply(@AuthenticationPrincipal @ApiIgnore final Member member
            ,@RequestParam("scriptUuid") String scriptUuid
            ,@RequestParam("upperReplyUuid") @Nullable String upperReplyUuid
            ,@RequestParam("content") String content
            ,@RequestParam("imgFile") @Nullable MultipartFile file) throws IOException {

        return responseReplyReturnSuccess(replyService.addReply(member,scriptUuid,upperReplyUuid,content,file));
    }


    @DeleteMapping("/reply/{replyUuid}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization : Bearer {token}", required = true
                    , dataType = "string", paramType = "header")
    })
    public BaseResponse<String> deleteReply(@PathVariable String replyUuid) {
        replyService.deleteReply(replyUuid);
        BaseResponse<String> response = new BaseResponse<>();
        response.setResult("삭제 가능합니다.");
        return  response;
    }

    private BaseResponse<List<Reply>> responseRepliesReturnSuccess(List<Reply> replies) {
        BaseResponse<List<Reply>> response = new BaseResponse<>();
        response.setResult(replies);
        return response;

    }

    private BaseResponse<Reply> responseReplyReturnSuccess(Reply reply) {
        BaseResponse<Reply> response = new BaseResponse<>();
        response.setResult(reply);
        return response;

    }

}
