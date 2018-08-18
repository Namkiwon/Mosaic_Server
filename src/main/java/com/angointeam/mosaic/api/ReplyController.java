package com.angointeam.mosaic.api;


import com.angointeam.mosaic.api.response.BaseResponse;
import com.angointeam.mosaic.domain.Reply;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/apis")
public class ReplyController {

    @GetMapping("/replies")
    BaseResponse<List<Reply>> getReplies(@RequestParam("scriptUuid") String scriptUuid) {
        return responseRepliesReturnSuccess(new ArrayList<>());
    }

    @PostMapping("/reply")
    BaseResponse<Reply> addReply(@RequestBody Reply reply, @RequestParam("imgFile") MultipartFile file) {
        return responseReplyReturnSuccess(reply);
    }

    @DeleteMapping("/reply/{replyUuid}")
    BaseResponse<String> deleteReply(@PathVariable String replyUuid) {
        BaseResponse<String> response = new BaseResponse<>();
        response.setResult(replyUuid);
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
