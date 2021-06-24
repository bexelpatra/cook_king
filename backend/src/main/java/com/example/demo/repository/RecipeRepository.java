package com.example.demo.repository;

import com.example.demo.dto.WrapperRecipe;
import com.example.demo.entity.RecipesEntity;
import com.example.demo.enums.FirstCategoryKind;
import com.example.demo.enums.SecondCategoryKind;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<RecipesEntity,Long> {

    Optional<RecipesEntity> getRecipesEntityById(int recipeId);
    List<RecipesEntity> findRecipesEntitiesByFirstCategoryKindOrderByIdDesc(FirstCategoryKind kind, Pageable page);
    List<RecipesEntity> findRecipesEntitiesByIdIsLessThanAndFirstCategoryKindOrderByIdDesc(int id, FirstCategoryKind kind, Pageable page);
    List<RecipesEntity> findRecipesEntitiesByIdIsLessThanAndFirstCategoryKindInAndSecondCategoryKindInOrderByIdDesc(int id, FirstCategoryKind[] fKinds, SecondCategoryKind[] sKinds, Pageable page);
    List<RecipesEntity> findRecipesEntitiesByIdIsLessThanAndFirstCategoryKindInAndSecondCategoryKindInAndTitleContainingOrderByIdDesc(int id, FirstCategoryKind[] fKinds, SecondCategoryKind[] sKinds, String keyword, Pageable page);

    @Query(value="select count(*) from users_favorite_recipes where recipes_id = ?1", nativeQuery = true)
    Integer getFavoriteUsersNumber(int id);

    @Query(value="select*, (select count(*) from users_favorite_recipes as inside where inside.recipes_id = outside.id) as counts from recipes as outside where outside.first_category = :firstKind and outside.id < :idLessThan order by counts,outside.id desc limit 20", nativeQuery = true)
    List<RecipesEntity> findPopular20Recipes(@Param("firstKind")int firstKind,@Param("idLessThan") int idLessThan);
}
