package com.angointeam.mosaic.repositories;

import com.angointeam.mosaic.domain.Script;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScriptsRepository extends JpaRepository<Script,Long> {
}
