package com.example.EnglishAppAPI.services.impls;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.EnglishAppAPI.entities.DiscussionTopic;
import com.example.EnglishAppAPI.entities.indexes.DiscussionDocument;
import com.example.EnglishAppAPI.mapstruct.dtos.*;
import com.example.EnglishAppAPI.entities.Discussion;
import com.example.EnglishAppAPI.entities.EnglishTopic;
import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.exceptions.NotFoundException;
import com.example.EnglishAppAPI.mapstruct.enums.DiscussionOrderBy;
import com.example.EnglishAppAPI.mapstruct.enums.NotificationType;
import com.example.EnglishAppAPI.mapstruct.mappers.DiscussionMapper;
import com.example.EnglishAppAPI.mapstruct.mappers.DiscussionTopicMapper;
import com.example.EnglishAppAPI.mapstruct.mappers.UserMapper;
import com.example.EnglishAppAPI.repositories.DiscussionTopicRepository;
import com.example.EnglishAppAPI.repositories.elas.DiscussionDocumentRepository;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.responses.ApiResponseStatus;
import com.example.EnglishAppAPI.repositories.DiscussionRepository;
import com.example.EnglishAppAPI.repositories.EnglishTopicRepository;
import com.example.EnglishAppAPI.repositories.UserRepository;
import com.example.EnglishAppAPI.services.interfaces.IDiscussionService;
import com.example.EnglishAppAPI.utils.ElasticsearchUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class DiscussionService implements IDiscussionService {
    private final DiscussionRepository discussionRepository;
    private final UserRepository userRepository;
    private final EnglishTopicRepository englishTopicRepository;
    private final DiscussionMapper discussionMapper;
    private final DiscussionDocumentRepository discussionDocumentRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final NotificationService notificationService;
    private final UserMapper userMapper;
    private final DiscussionTopicMapper discussionTopicMapper;
    private final DiscussionTopicRepository discussionTopicRepository;
    private final ElasticsearchClient elasticsearchClient;
    private final MissionService missionService;

    @Autowired
    public DiscussionService(DiscussionRepository discussionRepository, UserRepository userRepository, EnglishTopicRepository englishTopicRepository, DiscussionMapper discussionMapper, DiscussionDocumentRepository discussionDocumentRepository, SimpMessagingTemplate simpMessagingTemplate, NotificationService notificationService, UserMapper userMapper, DiscussionTopicMapper discussionTopicMapper, DiscussionTopicRepository discussionTopicRepository, ElasticsearchClient elasticsearchClient, MissionService missionService) {
        this.discussionRepository = discussionRepository;
        this.userRepository = userRepository;
        this.englishTopicRepository = englishTopicRepository;
        this.discussionMapper = discussionMapper;
        this.discussionDocumentRepository = discussionDocumentRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.notificationService = notificationService;
        this.userMapper = userMapper;
        this.discussionTopicMapper = discussionTopicMapper;
        this.discussionTopicRepository = discussionTopicRepository;
        this.elasticsearchClient = elasticsearchClient;
        this.missionService = missionService;
    }

    @Override
    public ResponseEntity<?> getDiscussion(Long id) {
        Discussion discussion = discussionRepository.findById(id).orElse(null);
        if (discussion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(ApiResponseStatus.FAIL, "get fail", ""));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.SUCCESS, "get discussion", discussionMapper.toDto(discussion)));
    }

    @Override
    public ResponseEntity<ApiResponse> getTopDiscussions() {
        List<Discussion> discussions = discussionRepository.getPopularDiscussions();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.SUCCESS, "Get top 5 sucessfully", discussions.stream().map(discussionMapper::toDto)));
    }

    @Override
    public Page<Discussion> getAllDiscussions(int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return discussionRepository.findAll(pageable);
    }

    @Override
    public ResponseEntity<ApiResponse> addNewDiscussion(DiscussionPostDto request) {
        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("Invalid user ID"));

        DiscussionTopic discussionTopic = discussionTopicRepository.findById(request.getDiscussionTopicId())
                .orElseThrow(() -> new NotFoundException("Invalid topic"));

        Discussion discussion = Discussion.builder()
                .title(request.getTitle())
                .createdDate(new Date())
                .updatedDate(new Date())
                .user(user)
                .topic(discussionTopic)
                .answers(new HashSet<>())
                .build();
        discussion = discussionRepository.save(discussion);
        discussionDocumentRepository.save(DiscussionDocument.fromEntity(discussion, userMapper.toElas(discussion.getUser()), discussionTopicMapper.toDto(discussion.getTopic())));

        missionService.updateMission(new UserMissionPostDto(user.getUserId(), 2L));

        for (UserEntity us : user.getFollowers()) {
            NotificationDto notificationDto = notificationService.addNotification(new NotificationPostDto(user.getUserId(), us.getUserId(), user.getFullName() + " the one you are following, created a new discussion!", false, NotificationType.DISCUSSION ,discussion.getId(), discussion.getId()));
            simpMessagingTemplate.convertAndSend("topic/user/notification/" + us.getUserId(), notificationDto);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ApiResponseStatus.SUCCESS, "Created a new discussion", discussionMapper.toDto(discussion)));
    }

    @Override
    public ResponseEntity<ApiResponse> updateDiscussion(Long discussionId, DiscussionPostDto discussion) {
        UserEntity user = userRepository.findById(discussion.getUserId())
                .orElseThrow(() -> new NotFoundException("Can't find the user"));
        Discussion dis = discussionRepository.findById(discussionId)
                .orElseThrow(() -> new NotFoundException("Can't find the discussion"));
        if (!user.getUserId().equals(dis.getUser().getUserId())) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ApiResponse(ApiResponseStatus.FAIL, "u dont own the discussion", ""));
        }
        DiscussionTopic discussionTopic = discussionTopicRepository.findById(discussion.getDiscussionTopicId())
                        .orElseThrow(() -> new NotFoundException("cant find discussion topic Id"));
        dis.setTopic(discussionTopic);
        dis.setTitle(discussion.getTitle());
        dis = discussionRepository.save(dis);
        updateDocument(dis);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.SUCCESS, "discussion was updated", discussionMapper.toDto(dis)));
    }

    @Override
    public ResponseEntity<ApiResponse> deleteDiscussion(Long discussionId) {
        discussionRepository.deleteById(discussionId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.SUCCESS, "discussion was deleted", ""));
    }

    @Override
    public Page<DiscussionDto> getUserDiscussions(int pageNumber, int pageSize, DiscussionOrderBy sortBy, Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Can't find the user"));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortBy == DiscussionOrderBy.id ? Sort.by(sortBy.toString()).ascending() : Sort.by(sortBy.toString()).descending());
        Page<Discussion> discussionsPage = discussionRepository.findByUser(user, pageable);
        return discussionsPage.map(discussionMapper::toDto);
    }

    @Override
    public Page<DiscussionDto> getDiscussionsByTopic(int pageNumber, int pageSize, DiscussionOrderBy sortBy, Long topicId) {
        EnglishTopic topic = englishTopicRepository.findById(topicId)
                .orElseThrow(() -> new NotFoundException("Can't find the topic with the provided id"));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortBy == DiscussionOrderBy.id ? Sort.by(sortBy.toString()).ascending() : Sort.by(sortBy.toString()).descending());
        Page<Discussion> discussionPage = discussionRepository.findByTopic(topic, pageable);
        return discussionPage.map(discussionMapper::toDto);
    }

    @Override
    public ResponseEntity<?> addTopic(DiscussionTopicPostDto dto) {
        DiscussionTopic discussionTopic = discussionTopicMapper.toEntity(dto);
        discussionTopicRepository.save(discussionTopic);
        return ResponseEntity.ok("ok");
    }

    @Override
    public ResponseEntity<?> getAllTopics() {
        List<DiscussionTopic> discussionTopics = discussionTopicRepository.findAll();
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get all topics", discussionTopics.stream().map(discussionTopicMapper::toDto)));
    }

    @Override
    public ResponseEntity<?> filterDiscussion(List<String> options, String searchTerms, int pageNumber, int pageSize) {
        Supplier<Query> boolQuery;
        if (options == null || options.isEmpty()) {
            boolQuery = () -> Query.of(q -> q.matchAll(ma -> ma.queryName("{}")));
        } else {
            boolQuery = () -> Query.of(q -> q.bool(b -> {
                for (String option : options) {
                    b.should(s -> s.match(m -> m.field("topic.name").query(option)));
                }
                return b;
            }));
        }
        Supplier<Query> combinedQuery = null;
        if (searchTerms != null) {
            System.out.println("not null");
            Supplier<Query> matchPhraseQuery = () -> Query.of(q -> q.matchPhrase(mp -> mp.field("title").query(searchTerms)));
            combinedQuery = () -> Query.of(q -> q.bool(b -> b.must(boolQuery.get()).must(matchPhraseQuery.get())));
        }

        SearchRequest searchRequest = new SearchRequest.Builder()
                .index("discussion_index")
                .query(combinedQuery == null ? boolQuery.get() : combinedQuery.get())
                .from(pageNumber)
                .size(pageSize)
                .sort(SortOptions.of(so -> so.field(f -> f.field("created_date").order(SortOrder.Desc))))
                .build();
        SearchResponse<DiscussionDocument> response = null;
        try {
            response = elasticsearchClient.search(searchRequest, DiscussionDocument.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Hit<DiscussionDocument>> hits = response.hits().hits();
        List<DiscussionDocument> userDocuments = hits.stream().map(Hit::source).toList();
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "filter", userDocuments));
    }

    private void updateDocument(Discussion discussion) {
        Optional<DiscussionDocument> discussionDocument = discussionDocumentRepository.findById(discussion.getId());
        if (discussionDocument.isPresent()) {
            DiscussionDocument discussionDoc = discussionDocument.get();
            discussionDoc.setTitle(discussion.getTitle());
            discussionDoc.setUser(userMapper.toElas(discussion.getUser()));
            discussionDoc.setTopic(discussionTopicMapper.toDto(discussion.getTopic()));
            discussionDoc.setCreated_date(discussion.getCreatedDate());
            discussionDoc.setUpdated_date(new Date());
            discussionDoc.setNumber_of_answers(discussion.getNumberOfAnswers());

            discussionDocumentRepository.save(discussionDoc);
        }
    }
}
