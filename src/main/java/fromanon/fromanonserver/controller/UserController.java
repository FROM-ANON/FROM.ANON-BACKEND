package fromanon.fromanonserver.controller;

import fromanon.fromanonserver.dto.AddUserRequest;
import fromanon.fromanonserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public String signup(AddUserRequest request){
        userService.save(request);
        return "success"; //회원 가입이 완료된 이후에 로그인 페이지로 이동
    }

}
