package fromanon.fromanonserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateTokensResponse {
    private String accessToken;
    private String refreshToken;
}
