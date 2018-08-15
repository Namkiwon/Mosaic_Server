package com.angointeam.mosaic.repositories;

import com.angointeam.mosaic.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    public Category findByUuid(String uuid);

}
