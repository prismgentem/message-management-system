package org.example.messagemanagementsystem.model.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageAnalysisResponse {
    private String result;
    private String analysis;
}
