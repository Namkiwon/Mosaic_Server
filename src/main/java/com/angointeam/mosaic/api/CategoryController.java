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
    public BaseResponse<List<Category>> getAll() {
        List<Category> categories = categoryService.getAllCategories();
        return responseCategoryListReturnSuccess(categories);
    }


    @PostMapping("/category")
    @ResponseBody
    public BaseResponse<Category> addCategory(String name , String emoji) throws IOException {
        Category category = categoryService.addCategory(name,emoji);
        return responseCategoryReturnSuccess(category);
    }

    @PutMapping("/category")
    @ResponseBody
    public BaseResponse<String> updateCategory(String categoryUuid ,String emoji) throws IOException {
        categoryService.updateEmoji(categoryUuid,emoji);
        return responseStringReturnSuccess("update finish");
    }

    private BaseResponse<String> responseStringReturnSuccess(String string) {

        BaseResponse<String> result = new BaseResponse<>();
        result.setResponseCode(0);
        result.setHttpStatus(200);
        result.setMessage(string);

        return result;
    }

    private BaseResponse<Category> responseCategoryReturnSuccess(Category category) {

        BaseResponse<Category> result = new BaseResponse<>();
        result.setResponseCode(0);
        result.setHttpStatus(200);
        result.setResult(category);

        return result;
    }

    private BaseResponse<List<Category>> responseCategoryListReturnSuccess(List<Category> categories) {

        BaseResponse<List<Category>> result = new BaseResponse<>();
        result.setResponseCode(0);
        result.setHttpStatus(200);
        result.setResult(categories);

        return result;
    }

}
