package com.example.demo.repository;

import com.example.demo.entity.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<EmailEntity,Long> {
    Optional<EmailEntity> findEmailEntityByEmailAndRegDateIsAfter(String email, Date date);
    // 가장 최근에 등록된 이메일을 가져온다.
    Optional<EmailEntity> findTopByEmailAndNumberAndRegDateIsAfterOrderByRegDateDesc(String email,String number, Date date);

    Optional<EmailEntity> findTopByEmailAndRegDateIsAfterOrderByRegDateDesc(String email, Date date);

    @Query(value = "select * from email :q",nativeQuery = true)
    List<EmailEntity> getListBy(@Param("q") String q);

    @Query(value = "select * from email <@ql.where > :q </@ql.where>",nativeQuery = true)
    Optional<EmailEntity> getOneBy(@Param("q") String q);
}
