package com.angointeam.mosaic.repositories;

import com.angointeam.mosaic.domain.Category;
import com.angointeam.mosaic.domain.Member;
import com.angointeam.mosaic.domain.Scrap;
import com.angointeam.mosaic.domain.Script;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScriptsRepository extends JpaRepository<Script,Long> {
    Script findByUuid(String uuid);

    Optional<Script> findScriptByUuid(String uuid);

    @Query("SELECT s From Script s Where  s.category.uuid = ?1 and s.valid = true")
    List<Script> findAllByCategoryUuid(String categoryUuid);


    @Query("SELECT s FROM Script s WHERE  s.content LIKE %?1% and s.valid = true")
    List<Script> findAllByKeyword(String keyword);

    @Query("SELECT s FROM Script s WHERE  s.writer.uuid = ?1 and s.valid = true")
    List<Script> findAllByMemberUuid(String memberUuid);

    @Transactional
    @Modifying
    @Query("UPDATE  Script s SET s.valid = false WHERE s.uuid = ?1")
    void updateScriptValidFalse(String ScriptUuid);

    @Query("SELECT s From Script s Where  s.valid = true")
    List<Script> findAllWhereValidTrue();


}
