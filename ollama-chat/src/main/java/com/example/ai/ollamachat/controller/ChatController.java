package com.example.ai.ollamachat.controller;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
public class ChatController {

    private final OllamaChatClient chatClient;

    @Autowired
    public ChatController(OllamaChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/ai/generate")
    public Map generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of("generation", chatClient.call(message));
    }

    @GetMapping("/ai/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return chatClient.stream(prompt);
    }

    public void d(){
        var ollamaApi = new OllamaApi();

        var chatClient = new OllamaChatClient(ollamaApi).withModel(MODEL)
                .withDefaultOptions(OllamaOptions.create()
                        .withModel(OllamaOptions.DEFAULT_MODEL)
                        .withTemperature(0.9f));

        ChatResponse response1 = chatClient.call(
                new Prompt("Generate the names of 5 famous pirates."));

        // Or with streaming responses
        Flux<ChatResponse> response2 = chatClient.stream(
                new Prompt("Generate the names of 5 famous pirates."));
    }
}
