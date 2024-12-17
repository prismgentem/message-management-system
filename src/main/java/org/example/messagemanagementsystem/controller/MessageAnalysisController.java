package org.example.messagemanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.messagemanagementsystem.model.request.MessageAnalysisRequest;
import org.example.messagemanagementsystem.model.response.MessageAnalysisResponse;
import org.example.messagemanagementsystem.service.MessageAnalysisService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/analysis")
public class MessageAnalysisController {
    private final MessageAnalysisService messageAnalysisService;
    @PostMapping
    public ResponseEntity<MessageAnalysisResponse> analyzeMessage(@RequestBody MessageAnalysisRequest request) {
        return ResponseEntity.ok(messageAnalysisService.gptAnalyzeMessage(request));
    }
}
