package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Page<Answer> findAll(Pageable pageable);
    @Query("SELECT a FROM Answer a WHERE a.discussion.id = :answerId ORDER BY a.createdAt DESC ")
    Page<Answer> findByQuestion(@Param("answerId") Long answerId, Pageable pageable);
    @Override
    boolean existsById(Long aLong);
}
