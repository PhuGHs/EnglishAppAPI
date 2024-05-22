package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationPostDto {
    @JsonProperty("sender_id")
    private Long senderId;
    @JsonProperty("receiver_id")
    private Long receiverId;
    @JsonProperty("message")
    private String message;
    @JsonProperty("is_read")
    private boolean isRead = false;
    @JsonProperty("created_item_id")
    private Long createdItemId;
    @JsonProperty("entity_item_id")
    private Long entityItemId;
}
