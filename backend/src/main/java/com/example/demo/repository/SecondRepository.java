package com.example.demo.repository;

import com.example.demo.entity.SecondCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecondRepository extends JpaRepository<SecondCategoryEntity,Long> {

}
