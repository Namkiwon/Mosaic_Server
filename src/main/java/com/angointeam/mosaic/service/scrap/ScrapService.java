package com.angointeam.mosaic.service.scrap;

import com.angointeam.mosaic.domain.Member;
import com.angointeam.mosaic.domain.Scrap;
import com.angointeam.mosaic.domain.Script;
import com.angointeam.mosaic.repositories.MemberRepository;
import com.angointeam.mosaic.repositories.ScrapRepository;
import com.angointeam.mosaic.repositories.ScriptsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Script addScrap(String scriptUuid,String memberUuid) {
        Scrap scrap = scrapRepository.findScrapByScriptUuidAndMemberUuid(scriptUuid,memberUuid);
        Script script = getScriptByUuid(scriptUuid);
        if(scrap == null){
            scrapRepository.save(new Scrap(script,memberUuid));
            script.setScrap(true);
        } else{
            scrapRepository.delete(scrap);
            script.setScrap(false);
        }
        return script;
    }

    public void deleteScrap(String scriptUuid,String memberUuid){
        scrapRepository.delete(scrapRepository.findScrapByScriptUuidAndMemberUuid(scriptUuid,memberUuid));
    }

    @Autowired
    ScriptsRepository scriptsRepository;

    public Script getScriptByUuid(String uuid){
        return scriptsRepository.findByUuid(uuid);
    }

}
