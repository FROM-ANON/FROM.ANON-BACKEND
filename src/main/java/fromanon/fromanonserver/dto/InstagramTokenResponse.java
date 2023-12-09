package fromanon.fromanonserver.dto;

import lombok.Getter;

@Getter
public class InstagramTokenResponse {
    private String access_token;
    private Long user_id;
}
