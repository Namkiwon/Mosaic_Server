package com.angointeam.mosaic.repositories;

import com.angointeam.mosaic.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    Category findByUuid(String uuid);

    @Transactional
    @Modifying
    @Query("update Category c set c.emoji = ?2 where c.uuid = ?1")
    void updateCategoryByUuid(String categoryUuid , String emoji);

}
