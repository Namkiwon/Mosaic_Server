package com.angointeam.mosaic.service.scripts;


import com.angointeam.mosaic.domain.Category;
import com.angointeam.mosaic.domain.Mem;
import com.angointeam.mosaic.domain.Scrap;
import com.angointeam.mosaic.domain.Script;
import com.angointeam.mosaic.repositories.CategoryRepository;
import com.angointeam.mosaic.repositories.MemRepository;
import com.angointeam.mosaic.repositories.ScrapRepository;
import com.angointeam.mosaic.repositories.ScriptsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ScriptsService {

    @Autowired
    private ScriptsRepository scriptsRepository;

    public Script getScriptByUuid(String uuid){
        return scriptsRepository.findByUuid(uuid);
    }

    public List<Script> getAllScripts() {
        return scriptsRepository.findAll();
    }

    public Script addScript(String uuid, String content, Category category, Mem writer, List<String> imgUrls,List<String> thumbnailUrls) {

        return scriptsRepository.save(new Script(uuid, content,category,writer,imgUrls,thumbnailUrls));
    }

    public void updateScript(String scriptUuid, Mem memberInfo,String content, Category category,List<String> imgUrls){
         scriptsRepository.updateScript(scriptUuid,memberInfo,content,category,imgUrls );
    }

    public void deleteScript(Script script){
        scriptsRepository.delete(script);
    }

    @Autowired
    private ScrapRepository scrapRepository;

    public List<String> getScrapUuidListByUuid(String memberUuid){
        return scrapRepository.findScriptUuidListByMemberUuid(memberUuid);
    }

    @Autowired
    private CategoryRepository categoryRepository;

    public Category getCategoryByUuid(String uuid) {
        return categoryRepository.findByUuid(uuid);
    }

    @Autowired
    private MemRepository memRepository;

    public Mem getWriter(String writerUuid) {
        return memRepository.findByUuid(writerUuid);
    }
}
