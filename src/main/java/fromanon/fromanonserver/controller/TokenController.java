package fromanon.fromanonserver.controller;

import fromanon.fromanonserver.dto.CreateAccessTokenResponse;
import fromanon.fromanonserver.dto.GenerateTokensResponse;
import fromanon.fromanonserver.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/token")
public class TokenController {
    private final TokenService tokenService;

    @GetMapping
    public ResponseEntity<GenerateTokensResponse> createNewAccessToken(@RequestHeader("Authorization") String authorizationHeader){
        String refreshToken = tokenService.getAccessTokenFromHeader(authorizationHeader);
        GenerateTokensResponse tokensResponse = tokenService.createNewAccessToken(refreshToken);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tokensResponse);
    }


}
