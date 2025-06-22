package com.alzamer.ahmad.springboot_rest_mcp.Controller;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.memory.repository.cassandra.CassandraChatMemoryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1/conversations")
public class ConversationController {
    private final CassandraChatMemoryRepository chatMemory;

    public ConversationController(CassandraChatMemoryRepository chatMemory) {
        this.chatMemory = chatMemory;
    }

    @GetMapping
    @Timed(value = "getConversations",percentiles = {.9,.95,.99})
    public List<String> getConversations(){
        return chatMemory.findConversationIds();
    }
}
