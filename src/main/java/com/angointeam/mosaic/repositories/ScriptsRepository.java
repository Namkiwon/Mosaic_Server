package com.angointeam.mosaic.repositories;

import com.angointeam.mosaic.domain.Category;
import com.angointeam.mosaic.domain.Member;
import com.angointeam.mosaic.domain.Script;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScriptsRepository extends JpaRepository<Script,Long> {
    Script findByUuid(String uuid);

    @Modifying
    @Query("update Script s set s.content = ?3,s.category = ?4, s.imgUrls =?5 where s.uuid = ?1 and s.writer = ?2")
    void updateScript(String scriptUuid, Member memberInfo, String content, Category category, List<String> imgUrls);

    @Query("SELECT s From Script s Where  s.category.uuid = ?1")
    List<Script> findAllByCategoryUuid(String categoryUuid);
}
