package org.example.messagemanagementsystem.model.openai.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class OpenAiRequest {

    @JsonProperty("model")
    private String model;

    @JsonProperty("messages")
    private List<OpenAiMessageRequest> messages;

    @JsonProperty("n")
    private Integer n;
}
