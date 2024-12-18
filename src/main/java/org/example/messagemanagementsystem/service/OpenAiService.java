package org.example.messagemanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.messagemanagementsystem.client.OpenAIClient;
import org.example.messagemanagementsystem.model.openai.request.OpenAiMessageRequest;
import org.example.messagemanagementsystem.model.openai.request.OpenAiRequest;
import org.example.messagemanagementsystem.model.request.MessageAnalysisRequest;
import org.example.messagemanagementsystem.model.response.MessageAnalysisResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenAiService {
    private final OpenAIClient openAIClient;
    @Value("${proxy-api.model}")
    private String model;

    public MessageAnalysisResponse getGptAnalyse(MessageAnalysisRequest request) {
        var gptRequest = createGptRequest(request);
        var response = openAIClient.postMessageToGpt(gptRequest);
        return MessageAnalysisResponse.builder()
                .result(String.valueOf(response.getChoices().get(0).getMessage().getContent()))
                .analysis("оценка от gpt")
                .build();
    }

    private OpenAiRequest createGptRequest(MessageAnalysisRequest request) {
        var promptString = String.format(
                "Твоя задача — классифицировать эмоциональную окраску сообщения на тему \"%s\". Ответ должен быть в двух частях, каждая часть должна содержать только одну цифру:\n" +
                        "1. В первой части верни только цифру: 1 — хорошая эмоция, 0 — плохая эмоция.\n" +
                        "Текст сообщения: \n", request.getMessage());

        var promptMessage = OpenAiMessageRequest.builder()
                .role("system")
                .content(promptString)
                .build();
        var userMessage = OpenAiMessageRequest.builder()
                .role("user")
                .content(request.getMessage())
                .build();

        return OpenAiRequest.builder()
                .model(model)
                .messages(List.of(promptMessage, userMessage))
                .n(1)
                .build();
    }
}
