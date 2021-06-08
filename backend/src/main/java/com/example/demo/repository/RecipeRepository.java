package com.example.demo.repository;

import com.example.demo.entity.RecipesEntity;
import com.example.demo.enums.FirstCategoryKind;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<RecipesEntity,Long> {
    List<RecipesEntity> findRecipesEntitiesByFirstCategoryEntityKindOrderByIdDesc(FirstCategoryKind kind, Pageable page);
    List<RecipesEntity> findRecipesEntitiesByIdIsLessThanAndFirstCategoryEntityKindOrderByIdDesc(int id,FirstCategoryKind kind, Pageable page);
}
