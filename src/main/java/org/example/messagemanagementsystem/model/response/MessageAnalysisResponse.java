package org.example.messagemanagementsystem.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageAnalysisResponse {
    private String result;
    private String analysis;
}
