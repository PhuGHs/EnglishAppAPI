package com.example.EnglishAppAPI.mapstruct.enums;

public enum NotificationType {
    FOLLOW("Follow"),
    REVIEW("Review"),
    DISCUSSION("Discussion"),
    ANSWER("Answer"),
    REPORT("Report"),
    LEARNINGROOM("LearningRoom");

    private String type;
    NotificationType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
