package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);
    Boolean existsByEmail(String email);
    @Query("select a from Account a where a.user.userId = :userId")
    Account findByUserId(Long userId);
}
