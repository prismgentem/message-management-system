package org.example.messagemanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.messagemanagementsystem.langmodel.MaxEntropyModel;
import org.example.messagemanagementsystem.model.request.MessageAnalysisRequest;
import org.example.messagemanagementsystem.model.response.MessageAnalysisResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaxEntropyService {
    private final MaxEntropyModel maxEntropyModel;
    public MessageAnalysisResponse analyseMessage(MessageAnalysisRequest request) {
        var result = maxEntropyModel.predictSentiment(request.getMessage());
        return MessageAnalysisResponse.builder()
                .result(result)
                .model("NaiveBayes")
                .build();
    }
}
