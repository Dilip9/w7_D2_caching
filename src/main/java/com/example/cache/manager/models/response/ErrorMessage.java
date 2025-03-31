package com.example.cache.manager.models.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessage {
    String errorCode;
    String errorMessage;
}
