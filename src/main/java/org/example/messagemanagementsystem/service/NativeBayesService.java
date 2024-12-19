package org.example.messagemanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.messagemanagementsystem.model.request.MessageAnalysisRequest;
import org.example.messagemanagementsystem.model.response.MessageAnalysisResponse;
import org.example.messagemanagementsystem.langmodel.NaiveBayesModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NativeBayesService {
    private final NaiveBayesModel naiveBayesModel;
    public MessageAnalysisResponse analyseMessage(MessageAnalysisRequest request) {
        var result = naiveBayesModel.predictSentiment(request.getMessage());
        return MessageAnalysisResponse.builder()
                .result(result)
                .model("NaiveBayes")
                .build();
    }
}
