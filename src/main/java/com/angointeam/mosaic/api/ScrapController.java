package com.angointeam.mosaic.api;

import com.angointeam.mosaic.domain.Category;
import com.angointeam.mosaic.domain.Scrap;
import com.angointeam.mosaic.service.category.CategoryService;
import com.angointeam.mosaic.service.scrap.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ScrapController {

    @Autowired
    ScrapService scrapService;

    @GetMapping("/scrap")
    public Scrap getScrap(String scriptUuid) {
        return scrapService.getScrapByUuid(scriptUuid);
    }

//    @GetMapping("/scraps")
//    public List<Scrap> getAllScraps() {
//        return scrapService.getAllScraps();
//    }

    @GetMapping("/scraps")
    public List<Scrap> getAll(String memberUuid) {
        return scrapService.getAllScrpasByMemberUuid(memberUuid);
    }

    @PostMapping("/scrap")
    @ResponseBody
    public Scrap addScrap(String scriptUuid,String memberUuid) throws IOException {
        return scrapService.addScrap(scrapService.getScriptByUuid(scriptUuid), memberUuid);
    }


}
