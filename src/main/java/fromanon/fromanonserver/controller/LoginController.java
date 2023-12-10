package fromanon.fromanonserver.controller;

import fromanon.fromanonserver.config.jwt.TokenProvider;
import fromanon.fromanonserver.domain.TempUser;
import fromanon.fromanonserver.domain.User;
import fromanon.fromanonserver.dto.*;
import fromanon.fromanonserver.repository.TempUserRepository;
import fromanon.fromanonserver.repository.UserRepository;
import fromanon.fromanonserver.service.TempUserService;
import fromanon.fromanonserver.service.TokenService;
import fromanon.fromanonserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class LoginController {
    private final UserService userService;
    private final TempUserService tempUserService;
    private final TokenService tokenService;

    @Value("${instagram.client.id}")
    private String clientId;
    @Value("${instagram.client.secret}")
    private String clientSecret;
    @Value("${instagram.redirect.uri}")
    private String redirectUri;
    @Value("${instagram.api.base-url}")
    private String apiUrl;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody InstagramLoginRequest request) {
        //code 값을 사용해 access_token, user_id 값을 얻는다.
        InstagramTokenResponse tokenResponse = getAccessToken(request.getCode());
        //user가 새로운 사용자인지 알아낸다.
        Boolean isNewUser = saveOrUpdate(tokenResponse);
        User user;
        try{
            user = userService.findByInstaUserId(tokenResponse.getUser_id());
        }catch(IllegalArgumentException ex){
            TempUser tempUser = tempUserService.findByInstaUserId(tokenResponse.getUser_id());
            user = User.builder()
                    .instaUserId(tempUser.getInstaUserId())
                    .instaId(tempUser.getInstaId())
                    .build();
        }

        //accessToken, refreshToken을 발급받는다.
        GenerateTokensResponse generateTokensResponse = tokenService.generateTokens(user);
        //LoginResponse를 만든다.
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setIsNewUser(isNewUser);
        loginResponse.setAccessToken(generateTokensResponse.getAccessToken());
        loginResponse.setRefreshToken(generateTokensResponse.getRefreshToken());
        return ResponseEntity.ok()
                .body(loginResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestHeader("Authorization") String authorizationHeader){
        String accessToken = tokenService.getAccessTokenFromHeader(authorizationHeader);
        Long tempUserId = tokenService.getInstaUserIdByToken(accessToken);
        TempUser tempUser = tempUserService.findByInstaUserId(tempUserId);
        User user = User.builder()
                .instaUserId(tempUser.getInstaUserId())
                .instaId(tempUser.getInstaId())
                .build();
        //데이터베이스에 유저를 저장한다.
        userService.saveUser(user);
        //임시 유저는 삭제한다.
        tempUserService.deleteUser(tempUser);

        return ResponseEntity.ok().build();
    }

    private InstagramTokenResponse getAccessToken(String code) {
        String url = "https://api.instagram.com/oauth/access_token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Instagram API에 전송할 파라미터 설정
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("grant_type", "authorization_code");
        formData.add("redirect_uri", redirectUri);
        formData.add("code", code);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);

        // RestTemplate을 사용하여 Instagram API에 요청
        ResponseEntity<InstagramTokenResponse> response = new RestTemplate().postForEntity(url, requestEntity, InstagramTokenResponse.class);

        // InstagramTokenResponse 객체에서 access_token과 user_id 추출
        InstagramTokenResponse tokenResponse = response.getBody();

        return tokenResponse;
    }

    private Boolean saveOrUpdate(InstagramTokenResponse response) {
        InstagramLoginResponse userInfo = getInstagramUser(response.getUser_id(), response.getAccess_token());

        //유저를 찾는다. 이미 존재한다면->instaId를 업데이트 / 존재하지 않는다면->회원가입
        try{
            User user = userService.findByInstaUserId(response.getUser_id());
            user.update(userInfo.getUsername());
            return false;
        }catch(IllegalArgumentException ex){
            TempUser newUser = TempUser.builder()
                    .instaId(userInfo.getUsername())
                    .instaUserId(response.getUser_id())
                    .build();
            // 데이터베이스에 임시 유저 저장
            tempUserService.saveUser(newUser);
            return true;
        }

    }


    public InstagramLoginResponse getInstagramUser(Long userId, String accessToken) {
        String url = String.format("%s/%s?fields=id,username&access_token=%s", apiUrl, userId, accessToken);

        // Instagram API에 GET 요청을 보내어 응답을 InstagramUser 객체로 매핑
        ResponseEntity<InstagramLoginResponse> response = new RestTemplate().getForEntity(url, InstagramLoginResponse.class);
        // InstagramUser 객체 반환
        return response.getBody();
    }

}
