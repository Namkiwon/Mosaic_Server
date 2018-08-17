package com.angointeam.mosaic.repositories;

import com.angointeam.mosaic.domain.Scrap;
import com.angointeam.mosaic.domain.Script;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap,Long> {

    Scrap findByScriptUuid(String uuid);

    List<Scrap> findAllByMemberUuid(String uuid);

    @Query("SELECT s.script From Scrap s Where  s.memberUuid = ?1 and s.script.valid = true")
    List<Script> findScriptListByMemberUuid(String uuid);

    @Query("SELECT s.script.uuid From Scrap s Where  s.memberUuid = ?1 and s.script.valid = true")
    List<String> findScriptUuidListByMemberUuid(String uuid);

    @Query("SELECT s From Scrap s Where  s.script.uuid = ?1 and s.memberUuid = ?2")
    Scrap findScrapByScriptUuidAndMemberUuid(String scriptUuid, String memberUuid);


//    @Query ("DELETE FROM Scrap s WHERE  s.memberUuid = ?2")
//    void deleteScrapByScriptUuidAndMemberUuid(String scriptUuid,String memberUuid);

}
