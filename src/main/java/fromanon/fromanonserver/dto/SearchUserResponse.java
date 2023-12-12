package fromanon.fromanonserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class SearchUserResponse {
    private Long userId;
    private Long instaUserId;
    private String instaId;
    private boolean pro;
}
