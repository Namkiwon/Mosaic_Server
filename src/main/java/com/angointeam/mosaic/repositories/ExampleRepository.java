package com.angointeam.mosaic.repositories;

import com.angointeam.mosaic.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ExampleRepository extends JpaRepository<Example,Long> {

}
