package com.angointeam.mosaic.api;

import com.angointeam.mosaic.domain.Category;
import com.angointeam.mosaic.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public List<Category> getAll() {
        return categoryService.getAllCategories();
    }


    @PostMapping("/category")
    @ResponseBody
    public Category addCategory(String name , String emoji) throws IOException {
        return categoryService.addCategory(UUID.randomUUID(),name,emoji);
    }


}
