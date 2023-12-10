package fromanon.fromanonserver.service;

import fromanon.fromanonserver.chatgpt.OpenAIProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenAIService {

    private final OpenAIProperties openAIProperties;

    public String getApiKey() {
        return openAIProperties.getApiKey();
    }
}
