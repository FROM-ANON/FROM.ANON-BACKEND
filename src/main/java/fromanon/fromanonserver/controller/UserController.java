package fromanon.fromanonserver.controller;

import fromanon.fromanonserver.domain.Mail;
import fromanon.fromanonserver.domain.User;
import fromanon.fromanonserver.dto.AddUserRequest;
import fromanon.fromanonserver.dto.MailResponse;
import fromanon.fromanonserver.dto.SearchUserResponse;
import fromanon.fromanonserver.service.TokenService;
import fromanon.fromanonserver.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;

    @DeleteMapping
    public ResponseEntity deleteUser(@RequestHeader("Authorization") String authorizationHeader){
        try {
            String accessToken = tokenService.getAccessTokenFromHeader(authorizationHeader);
            Long instaUserId = tokenService.getInstaUserIdByToken(accessToken);
            User user = userService.findByInstaUserId(instaUserId);

            userService.deleteUser(user);
            return ResponseEntity.ok()
                    .build();

        }catch(ExpiredJwtException ex){
            //accessToken 만료시 401코드를 반환한다.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    //사용자 검색
    @GetMapping("/search")
    public ResponseEntity<List<SearchUserResponse>> searchUser(@RequestParam String searchWord){

        List<SearchUserResponse> searchedUsers = userService.searchUserByInstaId(searchWord);

        if (searchedUsers.isEmpty()) {
            return ResponseEntity.ok().body(Collections.emptyList());
        } else {
            return ResponseEntity.ok().body(searchedUsers);
        }
    }
    @GetMapping("/{userId}")
    public ResponseEntity<SearchUserResponse> getUser(@PathVariable Long userId){
        SearchUserResponse user = userService.findUserDtoById(userId);
        return ResponseEntity.ok().body(user);
    }


}
