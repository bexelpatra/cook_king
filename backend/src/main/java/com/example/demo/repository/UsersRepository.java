package com.example.demo.repository;

import com.example.demo.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity,Long> {
    UsersEntity findUsersEntityById(long id);
    Optional<UsersEntity> findUsersEntityByToken(String token);
    List<UsersEntity> findUsersEntitiesByIdGreaterThanEqual(long id);
    Optional<UsersEntity> findUsersEntityByEmail(String email);

    @Modifying
    @Query(value = "insert into users_favorite_recipes value (?1,?2)", nativeQuery = true)
    @Transactional
    Integer addFavoriteRecipe(int usersId, int recipeId);

    @Modifying
    @Query(value = "delete from users_favorite_recipes where users_id = ?1 and recipes_id = ?2", nativeQuery = true)
    @Transactional
    Integer deleteFavoriteRecipe(int usersId, int recipeId);
}
