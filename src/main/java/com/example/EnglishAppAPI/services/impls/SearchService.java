package com.example.EnglishAppAPI.services.impls;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.EnglishAppAPI.entities.indexes.UserDocument;
import com.example.EnglishAppAPI.services.interfaces.ISearchService;
import com.example.EnglishAppAPI.utils.ElasticsearchUtils;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.elasticsearch.core.query.*;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class SearchService implements ISearchService {
    private static final String USER_INDEX = "users_index";

    @Autowired
    public SearchService(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    private final ElasticsearchClient elasticsearchClient;

    @Override
    public ResponseEntity<?> fuzzySearchUserFullName(String userName) throws IOException {
        Supplier<Query> supplier = ElasticsearchUtils.createSupplier(userName, "fullName");
        SearchResponse<UserDocument> response = elasticsearchClient.search(s->s.index(USER_INDEX).query(supplier.get()), UserDocument.class);
        List<Hit<UserDocument>> hits = response.hits().hits();
        System.out.println(hits);
        System.out.println(supplier.get());
        return ResponseEntity.ok(hits.stream().map(Hit::source));
    }

    @Override
    public ResponseEntity<?> recommendUsersBasedOnCommonInterests() {
        return null;
    }
}
