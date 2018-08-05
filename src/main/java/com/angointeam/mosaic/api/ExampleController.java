package com.angointeam.mosaic.api;


import com.angointeam.mosaic.domain.Example;
import com.angointeam.mosaic.service.example.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sample")
public class ExampleController {

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

}
