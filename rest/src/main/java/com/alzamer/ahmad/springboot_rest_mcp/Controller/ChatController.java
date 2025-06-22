package com.alzamer.ahmad.springboot_rest_mcp.Controller;

import com.alzamer.ahmad.springboot_rest_mcp.models.MyOllamaChatModel;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/chat")
@Slf4j
public class ChatController {
    private final MyOllamaChatModel chatModel;

    public ChatController(MyOllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping
    @Timed(value = "prompt",percentiles = {.9,.95,.99})
    public String prompt(@RequestParam String prompt,@RequestParam String conversationId){
        log.info("received new prompt request: [{}], conversationId: [{}]",prompt,conversationId);
        return chatModel.promptModel(prompt,conversationId);
    }
    @GetMapping("playwrightExpert")
    @Timed(value = "promptWithTooling",percentiles = {.9,.95,.99})
    public String promptWithTooling(@RequestParam String prompt,@RequestParam String conversationId){
        log.info(" playwrightExpert - received new prompt request : [{}], conversationId: [{}]",prompt,conversationId);
        return chatModel.promptModelWithTooling(prompt,conversationId);
    }

    @GetMapping("spring")
    @Timed(value = "promptSpring",percentiles = {.9,.95,.99})
    public String promptSpring(@RequestParam String prompt,@RequestParam String conversationId){
        log.info("spring - received new prompt request: [{}], conversationId: [{}]",prompt,conversationId);
        return chatModel.promptModelSpring(prompt,conversationId);
    }
}
