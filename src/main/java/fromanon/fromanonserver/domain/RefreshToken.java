package fromanon.fromanonserver.domain;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long instaUserId;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    public RefreshToken update(String newRefreshToken) {
        this.refreshToken = newRefreshToken;

        return this;
    }

    @Builder
    public RefreshToken(Long instaUserId, String refreshToken){
        this.instaUserId = instaUserId;
        this.refreshToken = refreshToken;
    }

}
