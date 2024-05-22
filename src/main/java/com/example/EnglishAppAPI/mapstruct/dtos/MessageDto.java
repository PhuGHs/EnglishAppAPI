package com.example.EnglishAppAPI.mapstruct.dtos;

import com.example.EnglishAppAPI.mapstruct.serializers.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.annotation.Nullable;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MessageDto {
    @JsonProperty("message_id")
    private Long messageId;
    @JsonProperty("message_room_id")
    private Long messageRoomId;
    @JsonProperty("sender")
    private UserNecessaryDto sender;
    @JsonProperty("receiver")
    private UserNecessaryDto receiver;
    @JsonProperty("message")
    private String message;
    @JsonProperty("is_read")
    private boolean isRead;
    @JsonProperty("created_at")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createdAt;
    @JsonProperty("image")
    private String image;
}
