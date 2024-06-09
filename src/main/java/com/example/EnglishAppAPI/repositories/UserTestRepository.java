package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.EnglishTest;
import com.example.EnglishAppAPI.entities.UserTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTestRepository extends JpaRepository<UserTest, Long> {
    @Query("select t from UserTest t where t.user.userId = :userId and t.test.englishLevel.levelId = :levelId")
    List<UserTest> getUserTests(@Param("userId") Long userId, @Param("levelId") Long levelId);

    @Query("select t from UserTest t where t.user.userId = :userId and t.test.englishTestId = :testId")
    UserTest getUserTestsByTestAndUser(@Param("userId") Long userId, @Param("testId") Long testId);
}
