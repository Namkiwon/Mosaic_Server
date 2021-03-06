package com.angointeam.mosaic.service.category;

import com.angointeam.mosaic.domain.Category;
import com.angointeam.mosaic.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategory(long idx) {
        return categoryRepository.findById(idx);
    }
    public Category addCategory(String name, String emoji) {
        return categoryRepository.save(new Category(UUID.randomUUID().toString() ,name,emoji));
    }

    public void updateEmoji(String categoryUuid, String emoji){
        categoryRepository.findByUuid(categoryUuid).map(category->{
            category.setEmoji(emoji);
                return category;
            }).map(categoryRepository::save)
                .orElseThrow(CategoryNotFoundException::new);
    }

}
