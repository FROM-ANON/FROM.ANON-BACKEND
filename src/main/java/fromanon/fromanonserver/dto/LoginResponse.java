package fromanon.fromanonserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private Boolean isNewUser;
}
