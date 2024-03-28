package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Long, Report> {
}
