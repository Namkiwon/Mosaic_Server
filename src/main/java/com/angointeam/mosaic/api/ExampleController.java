package com.angointeam.mosaic.api;


import com.angointeam.mosaic.domain.Example;
import com.angointeam.mosaic.etc.S3Uploader;
import com.angointeam.mosaic.service.example.ExampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sample")
public class ExampleController {

    public final S3Uploader s3Uploader;

    @Autowired
    ExampleService exampleService;

    @GetMapping("/examples")
    public List<Example> getAll() {
        return exampleService.getAllExample();
    }

    @PostMapping("/example")
    public Example add(@RequestBody String content) {
        return exampleService.addExample(content);
    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("data") MultipartFile multipartFile) throws IOException {
        return s3Uploader.upload(multipartFile, "static");
    }

}
