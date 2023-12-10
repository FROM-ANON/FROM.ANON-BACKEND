package fromanon.fromanonserver.chatgpt;

import fromanon.fromanonserver.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

@RequiredArgsConstructor
@Service
public class ChatGPTClient {

    private final OpenAIService openAIService;
    private String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    public boolean checkContent(String text) {
        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.setBearerAuth(openAIService.getApiKey());

        // 원하는 모델 지정
        String  model = "gpt-3.5-turbo";

        // 요청 바디 설정
        String requestBody = "{\"messages\": [{\"role\": \"system\", \"content\": \"You are a helpful assistant.\"}, {\"role\": \"user\", \"content\": \"\'" + text + "\' 라는 말에  때에 따라 기분이 나쁘거나, 성희롱으로 들릴 수 있는 말이 있어? 네/아니오 로만 답변해"+ "\"}], \"model\": \"" + model + "\"}";

        // HTTP 요청 생성
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // OpenAI API 호출
        ResponseEntity<String> responseEntity = new RestTemplate().exchange(OPENAI_API_URL, HttpMethod.POST, requestEntity, String.class);

        // API 응답 처리
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String response = responseEntity.getBody();
            System.out.println("OpenAI API 호출 response: " + response);
            return analyzeResponse(response);
        } else {
            // API 호출이 실패한 경우 처리
            System.out.println("OpenAI API 호출 실패: " + responseEntity.getStatusCode());
            return false;
        }
    }

    private boolean analyzeResponse(String response) {
        try {
            // JSON 형식의 응답 파싱
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray choices = jsonResponse.getJSONArray("choices");

            // 각 답변을 분석
            for (int i = 0; i < choices.length(); i++) {
                JSONObject choice = choices.getJSONObject(i);
                String content = choice.getJSONObject("message").getString("content");

                // "네" 라는 단어가 포함되어있으면 비방성 또는 성희롱성 텍스트였던 것으로 간주
                if (content.contains("네")){
                    System.out.println("내용에 비방성 또는 성희롱이 있습니다: " + content);
                    return true;
                }
            }
            // 여기까지 왔다면 비방성 또는 성희롱이 발견되지 않았음
            return false;
        } catch (Exception e) {
            // 예외 처리
            System.out.println("응답 분석 중 오류 발생: " + e.getMessage());
            return false;
        }
    }

}
