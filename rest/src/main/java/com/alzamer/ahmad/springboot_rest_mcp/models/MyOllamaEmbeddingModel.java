package com.alzamer.ahmad.springboot_rest_mcp.models;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MyOllamaEmbeddingModel {
    private final EmbeddingModel embeddingModel;


    public MyOllamaEmbeddingModel(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    public String promptModel(String prompt, String conversationId){
        var response = embeddingModel.embedForResponse(List.of("test"));
        return "asD";
    }
}