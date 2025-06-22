package com.alzamer.ahmad.springboot_rest_mcp.models;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

@Service
@Slf4j
public class MyOllamaChatModel {
    private final ChatClient  plainChatClient;
    private final ChatClient  springChatClient;
    private final ChatClient  playwrightChatClient;
    private final ChatModel chatModel;

    public MyOllamaChatModel(@Qualifier("plainChatClient") ChatClient plainChatClient1,@Qualifier("springBootChatClient") ChatClient springChatClient1, @Qualifier("playwrightChatClient") ChatClient playwrightChatClient1, ChatModel chatModel) {
        this.plainChatClient = plainChatClient1;
        this.springChatClient = springChatClient1;
        this.playwrightChatClient = playwrightChatClient1;
        this.chatModel = chatModel;
    }

    public String promptModel(String prompt, String conversationId){
        return plainChatClient.prompt(prompt)
                .advisors(conversationAdvisor(conversationId))
                .call()
                .content();
    }
    public String promptModelWithTooling(String prompt, String conversationId){
        return playwrightChatClient.prompt(prompt)
                .advisors(conversationAdvisor(conversationId))
                .call()
                .content();
    }

    public Map<String,Object> getModelInfo(){

        ChatOptions options = chatModel.getDefaultOptions();
        return Map.of("model", Objects.requireNonNullElse(options.getModel(),"NULL"),
                "MaxTokens",Objects.requireNonNullElse(options.getMaxTokens(),"NULL"),
                "FrequencyPenalty",Objects.requireNonNullElse(options.getFrequencyPenalty(),"NULL"),
                "Temperature",Objects.requireNonNullElse(options.getTemperature(),"NULL"),
                "TopK",Objects.requireNonNullElse(options.getTopK(),"NULL"),
                "TopP",Objects.requireNonNullElse(options.getTopP(),"NULL"),
                "StopSequences",Objects.requireNonNullElse(options.getStopSequences(),"NULL"));
    }

    public String promptModelSpring(String prompt, String conversationId){
        return springChatClient.prompt(prompt)
                .advisors(conversationAdvisor(conversationId))
                .call()
                .content();
    }

    private static Consumer<ChatClient.AdvisorSpec> conversationAdvisor(String conversationId) {
        return a -> a.param(ChatMemory.CONVERSATION_ID, conversationId);
    }
}