package org.example.messagemanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.messagemanagementsystem.model.request.MessageAnalysisRequest;
import org.example.messagemanagementsystem.model.response.MessageAnalysisResponse;
import org.example.messagemanagementsystem.repository.RedisCacheRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageAnalysisService {
    private final OpenAiService openAiService;
    private final RedisCacheRepository redisCacheRepositoryRepository;
    private final MaxEntropyService maxEntropyService;
    private final NativeBayesService nativeBayesService;

    public MessageAnalysisResponse getAnalyzeMessage(MessageAnalysisRequest request) {
//        String cacheKey = generateCacheKey(request);
//        MessageAnalysisResponse cachedResponse = (MessageAnalysisResponse) redisCacheRepositoryRepository.getCachedData(cacheKey);
//        if (cachedResponse != null) {
//            return cachedResponse;
//        }

        var response = switch (request.getModel()) {
            case "max-entropy" -> maxEntropyService.analyseMessage(request);
            case "native-bayes" -> nativeBayesService.analyseMessage(request);
            case "gpt" -> openAiService.getGptAnalyse(request);
            default -> throw new IllegalArgumentException("Invalid model");
        };

        //redisCacheRepositoryRepository.cacheData(cacheKey, response);
        return response;
    }

    private String generateCacheKey(MessageAnalysisRequest request) {
        return "MessageAnalysis:" + request.hashCode();
    }
}
