package fromanon.fromanonserver.service;

import fromanon.fromanonserver.domain.RefreshToken;
import fromanon.fromanonserver.domain.User;
import fromanon.fromanonserver.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken saveRefreshToken(RefreshToken refreshToken){
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
    }
}