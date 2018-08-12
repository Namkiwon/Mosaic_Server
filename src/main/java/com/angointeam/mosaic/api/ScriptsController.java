package com.angointeam.mosaic.api;

import com.angointeam.mosaic.domain.Example;
import com.angointeam.mosaic.domain.Script;
import com.angointeam.mosaic.etc.S3Uploader;
import com.angointeam.mosaic.service.scripts.ScriptsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ScriptsController {
    public final S3Uploader s3Uploader;

    @Autowired
    ScriptsService scriptsService;


    @GetMapping("/scripts")
    public List<Script> getAll() {
        return scriptsService.getAllScripts();
    }

    @PostMapping("/script")
    @ResponseBody
    public Script addScript(String content , String category_id, String writerUuid, @RequestParam("imgUrls") List<MultipartFile> multipartFiles) throws IOException {
        List<String> urls = new ArrayList<String>();
        for (int i = 0; i < multipartFiles.size(); i++) {
            urls.add(s3Uploader.upload(multipartFiles.get(i), "contentImage"));
        }
        System.out.println(scriptsService.getCategoryById(Long.parseLong(category_id)));
        return scriptsService.addScript(content, scriptsService.getCategoryById(Long.parseLong(category_id)), writerUuid, urls);
    }
}
