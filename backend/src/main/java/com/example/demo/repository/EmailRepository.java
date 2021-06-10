package com.example.demo.repository;

import com.example.demo.entity.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<EmailEntity,Long> {
    Optional<EmailEntity> findEmailEntityByEmailAndRegDateIsAfter(String email, Date date);
    // 가장 최근에 등록된 이메일을 가져온다.
    Optional<EmailEntity> findTopByEmailAndNumberAndRegDateIsAfterOrderByRegDateDesc(String email,String number, Date date);

    Optional<EmailEntity> findTopByEmailAndRegDateIsAfterOrderByRegDateDesc(String email, Date date);

    List<EmailEntity> findAllByEmail(String email);
    @Query(value = "select * from email :q",nativeQuery = true)
    List<EmailEntity> getListBy(@Param("q") String q);

    @Modifying
    @Transactional
    @Query(value="delete from email where email = ?1 and reg_date < str_to_date(?2,'%Y-%m-%d %H:%i:%s')", nativeQuery=true)
    void deleteEmail(@Param("email")String receiver,@Param("date")String now);

}
