package com.example.demo.repository;

import com.example.demo.entity.RecipesEntity;
import com.example.demo.enums.FirstCategoryKind;
import com.example.demo.enums.SecondCategoryKind;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<RecipesEntity,Long> {

    Optional<RecipesEntity> getRecipesEntityById(int recipeId);
    List<RecipesEntity> findRecipesEntitiesByFirstCategoryEntityKindOrderByIdDesc(FirstCategoryKind kind, Pageable page);
    List<RecipesEntity> findRecipesEntitiesByIdIsLessThanAndFirstCategoryEntityKindOrderByIdDesc(int id,FirstCategoryKind kind, Pageable page);
    List<RecipesEntity> findRecipesEntitiesByIdIsLessThanAndFirstCategoryEntityInAndSecondCategoryEntityInOrderByIdDesc(int id, FirstCategoryKind[] fKinds, SecondCategoryKind[] sKinds,Pageable page);
}
