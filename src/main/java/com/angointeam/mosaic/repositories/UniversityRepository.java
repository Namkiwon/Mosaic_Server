package com.angointeam.mosaic.repositories;

import com.angointeam.mosaic.domain.University;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface UniversityRepository extends JpaRepository<University,Long> {
    Optional<University> findByDomain(String domain);
}
