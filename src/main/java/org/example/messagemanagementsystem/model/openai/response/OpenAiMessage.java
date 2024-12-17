package org.example.messagemanagementsystem.model.openai.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OpenAiMessage {

    @JsonProperty("role")
    private String role;

    @JsonProperty("content")
    private String content;
}

