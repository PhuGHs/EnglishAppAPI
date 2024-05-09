package com.example.EnglishAppAPI.repositories.elas;

import com.example.EnglishAppAPI.entities.indexes.DiscussionDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussionDocumentRepository extends ElasticsearchRepository<DiscussionDocument, Long> {
}
