package fromanon.fromanonserver.controller;

import fromanon.fromanonserver.domain.FavoriteMailPaper;
import fromanon.fromanonserver.domain.MailPaper;
import fromanon.fromanonserver.domain.User;
import fromanon.fromanonserver.dto.FavoriteMailPaperResponse;
import fromanon.fromanonserver.dto.MailResponse;
import fromanon.fromanonserver.service.FavoriteMailPaperService;
import fromanon.fromanonserver.service.MailPaperService;
import fromanon.fromanonserver.service.TokenService;
import fromanon.fromanonserver.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/mailpaper/favorite")
public class FavoriteMailPaperController {
    private final FavoriteMailPaperService favoriteMailPaperService;
    private final TokenService tokenService;
    private final UserService userService;
    private final MailPaperService mailPaperService;

    @GetMapping
    public ResponseEntity<List<FavoriteMailPaperResponse>> getAllFMPsByUserId(@RequestHeader("Authorization") String authorizationHeader){
        try{
            String accessToken = tokenService.getAccessTokenFromHeader(authorizationHeader);
            Long instaUserId = tokenService.getInstaUserIdByToken(accessToken);
            User user = userService.findByInstaUserId(instaUserId);

            //해당 유저의 favoriteMailPaper만 가져온다.
            List<FavoriteMailPaperResponse> responseList = favoriteMailPaperService.findAllByUser(user);

            // 200 코드와 전체 메일 목록을 반환한다.
            return ResponseEntity.ok()
                    .body(responseList);
        }catch(ExpiredJwtException ex){
            //accessToken 만료시 401코드를 반환한다.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/{favoriteMailpaperId}")
    public ResponseEntity<FavoriteMailPaperResponse> getFavoriteMailpaper(@PathVariable Long favoriteMailpaperId){
        FavoriteMailPaperResponse response = favoriteMailPaperService.findById(favoriteMailpaperId);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/{mailPaperId}")
    public ResponseEntity addFMP(@PathVariable Long mailPaperId, @RequestHeader("Authorization") String authorizationHeader){
        try{
            String accessToken = tokenService.getAccessTokenFromHeader(authorizationHeader);
            Long instaUserId = tokenService.getInstaUserIdByToken(accessToken);
            User user = userService.findByInstaUserId(instaUserId);

            MailPaper mailPaper = mailPaperService.findById(mailPaperId);
            //FavoriteMailPaper를 save한다.
            favoriteMailPaperService.addFMP(mailPaper, user);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .build();
        }catch(ExpiredJwtException ex){
            //accessToken 만료시 401코드를 반환한다.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/{fmpId}")
    public ResponseEntity deleteFMP(@PathVariable Long fmpId, @RequestHeader("Authorization") String authorizationHeader){
        try{
            String accessToken = tokenService.getAccessTokenFromHeader(authorizationHeader);
            Long instaUserId = tokenService.getInstaUserIdByToken(accessToken);

            favoriteMailPaperService.deleteFMPById(fmpId);

            // 200 코드와 전체 메일 목록을 반환한다.
            return ResponseEntity.ok().build();
        }catch(ExpiredJwtException ex){
            //accessToken 만료시 401코드를 반환한다.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
