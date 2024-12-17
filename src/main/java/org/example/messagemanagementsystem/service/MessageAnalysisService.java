package org.example.messagemanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.messagemanagementsystem.model.request.MessageAnalysisRequest;
import org.example.messagemanagementsystem.model.response.MessageAnalysisResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageAnalysisService {
    private final OpenAiService openAiService;
    public MessageAnalysisResponse gptAnalyzeMessage(MessageAnalysisRequest request) {
        return openAiService.getGptAnalyse(request);
    }
}
