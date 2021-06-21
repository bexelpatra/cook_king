package com.example.demo.repository;

import com.example.demo.entity.ContentEntity;
import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ContentRepository extends JpaRepository<ContentEntity,Long> {
    @Query(value = "delete from content where recipes_id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteContents(int recipeId);
}
