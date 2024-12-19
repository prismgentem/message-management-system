package org.example.messagemanagementsystem.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageAnalysisRequest {
    private String message;
    private String model;
}
