package com.angointeam.mosaic.service.scrap;

import com.angointeam.mosaic.domain.Member;
import com.angointeam.mosaic.domain.Scrap;
import com.angointeam.mosaic.domain.Script;
import com.angointeam.mosaic.repositories.MemberRepository;
import com.angointeam.mosaic.repositories.ScrapRepository;
import com.angointeam.mosaic.repositories.ScriptsRepository;
import com.angointeam.mosaic.service.scripts.ScriptNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScrapService {

    @Autowired
    ScrapRepository scrapRepository;

    @Autowired
    ScriptsRepository scriptsRepository;

    public Scrap getScrapByUuid(String scriptUuid){
        return scrapRepository.findByScriptUuid(scriptUuid);
    };

    public List<Scrap> getAllScraps() {
        return scrapRepository.findAll();
    }

    public List<Script> getAllScrpasByMemberUuid(String memberUuid) {
       return  scrapRepository.findAllByMemberUuid(memberUuid).stream().map(scrap -> {
            scrap.getScript().setScrap(true);
            return scrap.getScript();
        }).collect(Collectors.toList());
    }

    public Script addScrap(String scriptUuid,String memberUuid) {
        return scriptsRepository.findScriptByUuid(scriptUuid).map(script -> {
              Optional<Scrap> scrap = scrapRepository.findScrapByScriptAndMemberUuid(script, memberUuid);
              if(scrap.isPresent()){
                  scrapRepository.delete(scrap.get());
                  script.setScrap(false);
              }else{
                  scrapRepository.save(new Scrap(script,memberUuid));
                  script.setScrap(true);
              }
            return script;
        }).orElseThrow(ScriptNotFoundException::new);
    }

    public void deleteScrap(String scriptUuid,String memberUuid){
        scriptsRepository.findScriptByUuid(scriptUuid).map(script -> {
            scrapRepository.findScrapByScriptAndMemberUuid(script, memberUuid).map(scrap -> {
                scrapRepository.delete(scrap);
                return scrap;
            }).orElseThrow(ScrapNotFoundException::new);
            return script;
        }).orElseThrow(ScriptNotFoundException::new);
    }


}
