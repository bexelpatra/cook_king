package com.example.demo.repository;

import com.example.demo.entity.CuisineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuisineRepository extends JpaRepository<CuisineEntity,Long> {

}
