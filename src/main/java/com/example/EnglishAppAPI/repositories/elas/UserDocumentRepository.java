package com.example.EnglishAppAPI.repositories.elas;

import com.example.EnglishAppAPI.entities.indexes.UserDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDocumentRepository extends ElasticsearchRepository<UserDocument, Long> {
}
