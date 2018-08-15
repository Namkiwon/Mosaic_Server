package com.angointeam.mosaic.api;

import com.angointeam.mosaic.api.response.BaseResponse;
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
    public BaseResponse<Category> addCategory(String name , String emoji) throws IOException {
        Category category = categoryService.addCategory(name,emoji);
        return responseCategoryReturnSuccess(category);
    }

    private BaseResponse<Category> responseCategoryReturnSuccess(Category category) {

        BaseResponse<Category> result = new BaseResponse<>();
        result.setResponseCode(0);
        result.setHttpStatus(200);
        result.setResult(category);

        return result;
    }

}
