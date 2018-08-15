package com.angointeam.mosaic.service.scrap;

import com.angointeam.mosaic.domain.Category;
import com.angointeam.mosaic.domain.Mem;
import com.angointeam.mosaic.domain.Scrap;
import com.angointeam.mosaic.domain.Script;
import com.angointeam.mosaic.repositories.MemRepository;
import com.angointeam.mosaic.repositories.ScrapRepository;
import com.angointeam.mosaic.repositories.ScriptsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ScrapService {

    @Autowired
    ScrapRepository scrapRepository;

    public Scrap getScrapByUuid(String scriptUuid){
        return scrapRepository.findByScriptUuid(scriptUuid);
    };

    public List<Scrap> getAllScraps() {
        return scrapRepository.findAll();
    }

    public List<Script> getAllScrpasByMemberUuid(String memberUuid) {
        List<Script> scrapList = scrapRepository.findScriptListByMemberUuid(memberUuid);
        for (int i = 0; i < scrapList.size(); i++){
            scrapList.get(i).setScrap(true);
        }
        return scrapList;
    }
    public Scrap addScrap(String scriptUuid,String memberUuid) {
        Script script = getScriptByUuid(scriptUuid);
        return scrapRepository.save(new Scrap(script,memberUuid));
    }

    public void deleteScrap(Scrap scrap){
        scrapRepository.delete(scrap);
    }

    @Autowired
    ScriptsRepository scriptsRepository;

    public Script getScriptByUuid(String uuid){
        return scriptsRepository.findByUuid(uuid);
    }

    @Autowired
    MemRepository memRepository;

    public Mem getMemByUuid(String uuid){
        return memRepository.findByUuid(uuid);
    }


}
