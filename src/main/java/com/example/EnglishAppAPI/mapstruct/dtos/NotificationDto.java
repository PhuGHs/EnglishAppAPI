package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationDto {
    @JsonProperty("notification_id")
    private Long id;
    @JsonProperty("sender")
    private UserNecessaryDto sender;
    @JsonProperty("receiver")
    private UserNecessaryDto receiver;
    @JsonProperty("message")
    private String message;
    @JsonProperty("is_read")
    private boolean isRead;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("created_item_id")
    private Long createdItemId;
    @JsonProperty("entity_item_id")
    private Long entityItemId;
}
