package com.github.kito.docai.domain.chat;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.kito.docai.services.RagService;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final RagService ragService;

    public ChatController(RagService ragService) {
        this.ragService = ragService;
    }

    @PostMapping
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        if (request == null || request.question() == null || request.question().isBlank()) {
            return ResponseEntity.badRequest().body(new ChatResponse("Question cannot be blank."));
        }

        String answer = ragService.askQuestion(request.question(), request.documentId());
        return ResponseEntity.ok(new ChatResponse(answer));
    }
}
