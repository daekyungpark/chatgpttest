package com.example.chatgpt;


import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/chat-gpt")
public class testController {

    private final ChatGptService chatGptService;

    @PostMapping("/question")
    public ChatGptResponseDto sendQuestion(@RequestBody QuestionRequestDto requestDto) {
        return chatGptService.askQuestion(requestDto);
    }

    @PostMapping("/question2")
    public ChatMessage sendQuestion2(@RequestBody QuestionRequestDto requestDto) {

        OpenAiService service = new OpenAiService("your api key");

        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage("user", requestDto.getQuestion());
        messages.add(chatMessage);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .temperature(1.0)
                .n(1)
                .stream(Boolean.FALSE)
                .maxTokens(200)
                .presencePenalty(0.0)
                .frequencyPenalty(0.0)
                .build();

        ChatMessage responseMessage = service.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage();
//
        return responseMessage;
    }
}
