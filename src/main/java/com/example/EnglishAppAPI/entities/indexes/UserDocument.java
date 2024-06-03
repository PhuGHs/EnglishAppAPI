package com.example.EnglishAppAPI.entities.indexes;

import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.mapstruct.dtos.InterestDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "user_index", createIndex = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "_class")
public class UserDocument {
    private Long id;
    @Field(name = "fullName", type = FieldType.Text)
    private String fullName;
    @Field(name = "gender", type = FieldType.Boolean)
    private boolean gender = false;
    @Field(name = "quote", type = FieldType.Text)
    private String quote = "";
    @Field(name = "profilePicture", type = FieldType.Text)
    private String profilePicture = "";
    @Field(name = "followingCount", type = FieldType.Integer)
    private int followingCount = 0;
    @Field(name = "followersCount", type = FieldType.Integer)
    private int followersCount = 0;
    @Field(name = "englishLevel", type = FieldType.Text)
    private String englishLevel;
    @Field(name = "interests")
    private List<InterestDto> interests;

    private transient long sharedInterestCount;

    public static UserDocument fromUserEntity(UserEntity userEntity, List<InterestDto> interests) {
        UserDocument userDocument = new UserDocument();
        userDocument.id = userEntity.getUserId();
        userDocument.fullName = userEntity.getFullName();
        userDocument.gender = userEntity.isGender();
        userDocument.quote = userEntity.getQuote();
        userDocument.profilePicture = userEntity.getProfilePicture();
        userDocument.followingCount = userEntity.getFollowingCount();
        userDocument.followersCount = userEntity.getFollowersCount();
        userDocument.englishLevel = userEntity.getEnglishLevel().getLevelName();
        userDocument.interests = interests;
        return userDocument;
    }
}
