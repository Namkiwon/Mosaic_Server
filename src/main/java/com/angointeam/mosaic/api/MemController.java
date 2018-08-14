package com.angointeam.mosaic.api;

import com.angointeam.mosaic.domain.Category;
import com.angointeam.mosaic.domain.Mem;
import com.angointeam.mosaic.service.category.CategoryService;
import com.angointeam.mosaic.service.mem.MemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemController {

    @Autowired
    MemService memService;

    @GetMapping("/mems")
    public List<Mem> getAll() {
        return memService.getAllMem();
    }


    @PostMapping("/mem")
    @ResponseBody
    public Mem addMem() throws IOException {
        return memService.addMem();
    }
}
