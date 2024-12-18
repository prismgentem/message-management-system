package org.example.messagemanagementsystem.model.openai.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Choice {

    @JsonProperty("index")
    private Integer index;

    @JsonProperty("message")
    private OpenAiMessage message;

    @JsonProperty("finish_reason")
    private String finishReason;
}

