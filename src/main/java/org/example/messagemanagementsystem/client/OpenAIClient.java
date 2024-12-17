package org.example.messagemanagementsystem.client;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.messagemanagementsystem.model.openai.request.OpenAiRequest;
import org.example.messagemanagementsystem.model.openai.response.OpenAiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class OpenAIClient {
    private final RestTemplate restTemplate;
    @Value("${proxy-api.url}")
    private String baseUrl;
    @Value("${proxy-api.key}")
    private String token;

    public OpenAiResponse postMessageToGpt(OpenAiRequest request) {
        var url = baseUrl + "/v1/chat/completions";
        var httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.set("Authorization", "Bearer " + token);

        var httpEntity = new HttpEntity<>(request, httpHeaders);
        var responseEntity = restTemplate.exchange(
                url, HttpMethod.POST, httpEntity, OpenAiResponse.class
        );

        return responseEntity.getBody();
    }
}
