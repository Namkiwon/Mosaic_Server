package com.angointeam.mosaic.service.scripts;


import com.angointeam.mosaic.domain.Category;
import com.angointeam.mosaic.domain.Script;
import com.angointeam.mosaic.repositories.CategoryRepository;
import com.angointeam.mosaic.repositories.ScriptsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ScriptsService {

    @Autowired
    private ScriptsRepository scriptsRepository;

    public List<Script> getAllScripts() {
        return scriptsRepository.findAll();
    }

    public Script addScript(String content, Category category, String writerUuid, List<String> imgUrls) {

        return scriptsRepository.save(new Script(content,category,writerUuid,imgUrls));
    }



    @Autowired
    private CategoryRepository categoryRepository;


    public Category getCategoryById(Long id) {
        return categoryRepository.getOne(id);
    }
}
