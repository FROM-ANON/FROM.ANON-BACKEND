package fromanon.fromanonserver.repository;

import fromanon.fromanonserver.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByInstaUserId(Long instaUserId);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
