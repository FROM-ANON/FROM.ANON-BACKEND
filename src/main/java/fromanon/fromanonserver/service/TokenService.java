package fromanon.fromanonserver.service;
import fromanon.fromanonserver.config.jwt.TokenProvider;
import fromanon.fromanonserver.domain.RefreshToken;
import fromanon.fromanonserver.domain.User;
import fromanon.fromanonserver.dto.GenerateTokensResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    //accessToken, refreshToken 생성
    public GenerateTokensResponse generateTokens(User user){
        String accessToken = tokenProvider.makeAccessToken(Duration.ofHours(1), user);
        String refreshToken = tokenProvider.makeRefreshToken(Duration.ofDays(14));
        //refreshToken 유효성 검사에 사용할 refreshToken 객체를 만든다.
        RefreshToken rft = RefreshToken.builder()
                .instaUserId(user.getInstaUserId())
                .refreshToken(refreshToken)
                .build();
        refreshTokenService.saveRefreshToken(rft);

        GenerateTokensResponse response = new GenerateTokensResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);

        return response;
    }

    //refreshtoken을 받아 유효성 검사를 한 뒤 새로운 엑세스 토큰을 발급한다.
    public GenerateTokensResponse createNewAccessToken(String refreshToken) {
        // 토큰 유효성 검사에 실패하면 예외 발생
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }
        //토큰으로 유저를 찾는다.
        Long instaUserId = refreshTokenService.findByRefreshToken(refreshToken).getInstaUserId();
        User user = userService.findByInstaUserId(instaUserId);
        //새로운 엑세스 토큰 생성
        return generateTokens(user);
    }

    //헤더에서 Bearer을 제외한 accessToken 값만 반환한다.
    public String getAccessTokenFromHeader(String authorizationHeader)
    {
        return authorizationHeader.substring("Bearer ".length());
    }

    //토큰으로 유저 ID 값을 찾아 반환한다.
    public Long getInstaUserIdByToken(String token){
        return tokenProvider.getInstaUserId(token);
    }
}
