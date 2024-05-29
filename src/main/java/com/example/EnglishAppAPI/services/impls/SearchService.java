package com.example.EnglishAppAPI.services.impls;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TermsQueryField;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.EnglishAppAPI.entities.EnglishLevel;
import com.example.EnglishAppAPI.entities.Interest;
import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.entities.indexes.UserDocument;
import com.example.EnglishAppAPI.exceptions.NotFoundException;
import com.example.EnglishAppAPI.mapstruct.mappers.UserMapper;
import com.example.EnglishAppAPI.repositories.UserRepository;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.responses.ApiResponseStatus;
import com.example.EnglishAppAPI.services.interfaces.ISearchService;
import com.example.EnglishAppAPI.utils.ElasticsearchUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class SearchService implements ISearchService {
    private static final String USER_INDEX = "users_index";

    @Autowired
    public SearchService(ElasticsearchClient elasticsearchClient, UserRepository userRepository, UserMapper userMapper) {
        this.elasticsearchClient = elasticsearchClient;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    private final ElasticsearchClient elasticsearchClient;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public ResponseEntity<?> fuzzySearchUserFullName(String userName, Long currentUserId) throws IOException {
        Supplier<Query> supplier = ElasticsearchUtils.createSupplier(userName, "fullName");
        Supplier<Query> exclusionQuerySupplier = ElasticsearchUtils.createExclusionQuery(currentUserId, "id");
        Supplier<Query> combinedQuerySupplier = () -> Query.of(q ->
                q.bool(b -> b.mustNot(exclusionQuerySupplier.get()).must(supplier.get()))
        );
        SearchResponse<UserDocument> response = elasticsearchClient.search(s->s.index(USER_INDEX).query(combinedQuerySupplier.get()), UserDocument.class);
        List<Hit<UserDocument>> hits = response.hits().hits();
        return ResponseEntity.ok(hits.stream().map(Hit::source));
    }

    @Override
    public ResponseEntity<?> recommendUsersBasedOnCommonInterests(Long currentUserId, int page, int size) throws IOException {
        List<String> interestNames = userRepository.findById(currentUserId).orElseThrow(() -> new NotFoundException("cant find the user")).getInterests().stream().map(Interest::getInterestName).toList();
        
        Supplier<Query> interestQuerySupplier = () -> Query.of(q -> q.terms(t -> t.field("interests.name")
                .terms(TermsQueryField.of(tf -> tf.value(interestNames.stream().map(FieldValue::of).collect(Collectors.toList()))))));

        Supplier<Query> exclusionQuerySupplier = ElasticsearchUtils.createExclusionQuery(currentUserId, "id");

        Supplier<Query> combinedQuerySupplier = () -> Query.of(q -> q.bool(b -> b
                .must(interestQuerySupplier.get())
                .mustNot(exclusionQuerySupplier.get())
        ));

        SearchRequest searchRequest = new SearchRequest.Builder()
                .index("users_index")
                .query(combinedQuerySupplier.get())
                .from(page*size)
                .size(size)
                .sort(SortOptions.of(so -> so.field(f -> f.field("followersCount").order(SortOrder.Desc))))
                .build();

        SearchResponse<UserDocument> response = elasticsearchClient.search(searchRequest, UserDocument.class);
        List<Hit<UserDocument>> hits = response.hits().hits();
        List<UserDocument> userDocuments = hits.stream().map(Hit::source).collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "recommend users", userDocuments));
    }

    @Override
    public ResponseEntity<?> recommendUsersBasedOnEnglishLevel(Long currentUserId, int pageNumber, int pageSize) {
        EnglishLevel englishLevel = userRepository.findById(currentUserId)
                .orElseThrow(() -> new NotFoundException("cant find the user")).getEnglishLevel();
//        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "followersCount"));
//        Page<UserEntity> userEntities = userRepository.findUsersByEnglishLevelAndSortByFollowersCountDesc(englishLevel, pageable);
//        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get users by english level", userEntities.map(userMapper::toFindDto)));

        Supplier<Query> englishLevelQuerySupplier = () -> Query.of(q -> q.term(t -> t.field("englishLevel").value(englishLevel.getLevelName())));

        Supplier<Query> exclusionQuerySupplier = ElasticsearchUtils.createExclusionQuery(currentUserId, "id");

        Supplier<Query> combinedQuerySupplier = () -> Query.of(q -> q.bool(b -> b
                .must(englishLevelQuerySupplier.get())
                .mustNot(exclusionQuerySupplier.get())
        ));

        int from = pageNumber * pageSize;

        SearchRequest searchRequest = new SearchRequest.Builder()
                .index("users_index")
                .query(combinedQuerySupplier.get())
                .from(from)
                .size(pageSize)
                .sort(SortOptions.of(so -> so.field(f -> f.field("followersCount").order(SortOrder.Desc))))
                .build();

        SearchResponse<UserDocument> response = null;
        try {
            response = elasticsearchClient.search(searchRequest, UserDocument.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Hit<UserDocument>> hits = response.hits().hits();
        List<UserDocument> userDocuments = hits.stream().map(Hit::source).toList();
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "recommend users based on english level", userDocuments));
    }
}
