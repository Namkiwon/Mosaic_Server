package com.angointeam.mosaic.repositories;

import com.angointeam.mosaic.domain.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap,Long> {

    public Scrap findByScriptUuid(String uuid);

    public List<Scrap> findAllByMemberUuid(String uuid);

    @Query("SELECT s.script.uuid From Scrap s Where  s.memberUuid = ?1")
    public List<String> findScriptUuidListByMemberUuid(String uuid);

}
