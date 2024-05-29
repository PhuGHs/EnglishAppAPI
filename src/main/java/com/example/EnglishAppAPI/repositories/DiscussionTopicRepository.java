package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.DiscussionTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussionTopicRepository extends JpaRepository<DiscussionTopic, Long> {
}
