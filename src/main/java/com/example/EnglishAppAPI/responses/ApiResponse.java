package com.example.EnglishAppAPI.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse {
    private ApiResponseStatus status;
    private String message;
    private Object data;

    public ApiResponse(ApiResponseStatus status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}

