package fromanon.fromanonserver.controller;

import fromanon.fromanonserver.domain.User;
import fromanon.fromanonserver.dto.AddUserRequest;
import fromanon.fromanonserver.dto.MailResponse;
import fromanon.fromanonserver.dto.SearchUserResponse;
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

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId){
        User user = userService.findById(userId);
        userService.deleteUser(user);
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
