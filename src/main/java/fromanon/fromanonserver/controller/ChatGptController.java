package fromanon.fromanonserver.controller;

import fromanon.fromanonserver.chatgpt.ChatGPTClient;
import fromanon.fromanonserver.dto.ChatRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chatgpt")
public class ChatGptController {
    private final ChatGPTClient chatGPTClient;

    @PostMapping
    public ResponseEntity checkContent(@RequestBody ChatRequest request){
        String content = request.getContent();
        if(chatGPTClient.checkContent(content)){
            // 비방성 - 성희롱성 문구일시 406 상태 코드 반환
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .build();

        }else{
            return ResponseEntity.ok()
                    .build();
        }
    }
}
