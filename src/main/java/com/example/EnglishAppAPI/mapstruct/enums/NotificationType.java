package com.example.EnglishAppAPI.mapstruct.enums;


public enum NotificationType {
    FOLLOW("Follow"),
    REVIEW("Review"),
    DISCUSSION("Discussion"),
    ANSWER("Answer"),
    LEARNINGROOM("LearningRoom"),
    REPORT("Report");

    private String type;
    NotificationType(String type) {
        this.type = type;
    }

}
