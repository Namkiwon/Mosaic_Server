package com.angointeam.mosaic.repositories;

import com.angointeam.mosaic.domain.Mem;
import com.angointeam.mosaic.domain.Script;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MemRepository extends JpaRepository<Mem,Long> {
    public Mem findByUuid(String uuid);
}
