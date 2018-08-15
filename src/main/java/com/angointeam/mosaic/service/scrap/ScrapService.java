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

    public List<Scrap> getAllScrpasByMemberUuid(String memberUuid) {
        return scrapRepository.findAllByMemberUuid(memberUuid);
    }
    public Scrap addScrap(Script script,String memberUuid) {
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
