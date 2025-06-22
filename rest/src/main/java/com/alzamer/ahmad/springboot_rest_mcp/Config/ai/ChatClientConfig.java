package com.alzamer.ahmad.springboot_rest_mcp.Config.ai;

import io.modelcontextprotocol.client.McpSyncClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatClientConfig {
    @Bean("plainChatClient")
    public ChatClient plainChatClient(ChatModel chatModel, ChatMemory chatMemory, List<McpSyncClient> mcpSyncClients) {
        return ChatClient.builder(chatModel)
                .defaultAdvisors(new SimpleLoggerAdvisor(), MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultToolCallbacks(new SyncMcpToolCallbackProvider(mcpSyncClients))
                .build();

    }

    @Bean("springBootChatClient")
    public ChatClient springBootChatClient(ChatModel chatModel, ChatMemory chatMemory, VectorStore vectorStore, PromptTemplate ragPromptTemplate ) {
        return ChatClient.builder(chatModel)
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        QuestionAnswerAdvisor.builder(vectorStore).promptTemplate(ragPromptTemplate).build()
                )
                .defaultSystem("you are a senior Spring/SpringBoot developer. you always follow best practices and document your code with inline comments.")
                .build();

    }
    @Bean("playwrightChatClient")
    public ChatClient playwrightChatClient(ChatModel chatModel, ChatMemory chatMemory, List<McpSyncClient> mcpSyncClients) {
        return ChatClient.builder(chatModel)
                .defaultSystem("You are useful QA tester who has access to the playwright mcp and its tools and can issue command to it")
                .defaultToolCallbacks(new SyncMcpToolCallbackProvider(mcpSyncClients))
                .defaultAdvisors(new SimpleLoggerAdvisor(),MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();

    }
}
