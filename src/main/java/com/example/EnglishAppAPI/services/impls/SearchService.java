package com.example.EnglishAppAPI.services.impls;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
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
    public ResponseEntity<?> recommendUsersBasedOnCommonInterests(Long currentUserId) {
        Set<Interest> currentUserInterests = userRepository.findById(currentUserId)
                .orElseThrow(() -> new NotFoundException("user not found")).getInterests();

        List<UserEntity> recommendedUsers = userRepository.findAll()
                .stream()
                .filter(user -> !Objects.equals(user.getUserId(), currentUserId) && !user.getInterests().isEmpty())
                .sorted((u1, u2) -> {
                    int commonInterests = 0;
                    for (Interest interest : currentUserInterests) {
                        if (u1.getInterests().contains(interest)) {
                            commonInterests++;
                        }
                        if (u2.getInterests().contains(interest)) {
                            commonInterests++;
                        }
                    }
                    return Integer.compare(commonInterests, 0);
                })
                .toList();
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "recommend users", recommendedUsers.stream().map(userMapper::toFindDto)));
    }

    @Override
    public ResponseEntity<?> recommendUsersBasedOnEnglishLevel(Long currentUserId, int pageNumber, int pageSize) {
        EnglishLevel englishLevel = userRepository.findById(currentUserId)
                .orElseThrow(() -> new NotFoundException("cant find the user")).getEnglishLevel();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "followersCount"));
        Page<UserEntity> userEntities = userRepository.findUsersByEnglishLevelAndSortByFollowersCountDesc(englishLevel, pageable);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get users by english level", userEntities.map(userMapper::toFindDto)));
    }
}
