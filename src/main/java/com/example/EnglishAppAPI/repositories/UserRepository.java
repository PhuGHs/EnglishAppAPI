package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.EnglishLevel;
import com.example.EnglishAppAPI.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Override
    boolean existsById(Long aLong);
    @Query("SELECT u FROM UserEntity u JOIN u.followers f WHERE u.englishLevel = :englishLevel GROUP BY u ORDER BY COUNT(f) DESC")
    Page<UserEntity> findUsersByEnglishLevelAndSortByFollowersCountDesc(@Param("englishLevel")EnglishLevel englishLevel, Pageable pageable);
    @Query("SELECT u.followers from UserEntity u WHERE u.userId = :currentUserId")
    Page<UserEntity> getFollowers(@Param("currentUserId") Long currentUserId, Pageable pageable);
    @Query("SELECT u.following from UserEntity u WHERE u.userId = :currentUserId")
    Page<UserEntity> getFollowing(@Param("currentUserId") Long currentUserId, Pageable pageable);
}
