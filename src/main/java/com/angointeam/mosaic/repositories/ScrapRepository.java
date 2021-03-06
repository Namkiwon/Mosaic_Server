package com.angointeam.mosaic.repositories;

import com.angointeam.mosaic.domain.Scrap;
import com.angointeam.mosaic.domain.Script;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap,Long> {

    Scrap findByScriptUuid(String uuid);

    List<Scrap> findAllByMemberUuid(String uuid);

    @Query("SELECT s.script.uuid From Scrap s Where  s.memberUuid = ?1 and s.script.valid = true")
    List<String> findScriptUuidListByMemberUuid(String uuid);


    Optional<Scrap> findScrapByScriptAndMemberUuid(Script script, String memberUuid);

}
