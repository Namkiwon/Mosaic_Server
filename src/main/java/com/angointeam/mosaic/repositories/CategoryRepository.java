package com.angointeam.mosaic.repositories;

import com.angointeam.mosaic.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    public Category findByUuid(UUID uuid);

}
