package fromanon.fromanonserver.controller;

import fromanon.fromanonserver.domain.Mail;
import fromanon.fromanonserver.domain.User;
import fromanon.fromanonserver.dto.MailResponse;
import fromanon.fromanonserver.dto.SendMailRequest;
import fromanon.fromanonserver.service.MailService;
import fromanon.fromanonserver.service.TokenService;
import fromanon.fromanonserver.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping("/mail")
public class MailController {

    private final MailService mailService;
    private final TokenService tokenService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity sendMail(@RequestBody SendMailRequest request){
        request.setRead(false);
        Mail savedMail = mailService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping
    public ResponseEntity<List<MailResponse>> getAllMails(@RequestHeader("Authorization") String authorizationHeader) {
        try{
            String accessToken = tokenService.getAccessTokenFromHeader(authorizationHeader);
            Long instaUserId = tokenService.getInstaUserIdByToken(accessToken);
            User user = userService.findByInstaUserId(instaUserId);

            //해당 유저의 mail만 가져온다.
            List<MailResponse> allMails = mailService.findByUser(user)
                    .stream()
                    .map(MailResponse::new)
                    .toList();

            // 200 코드와 전체 메일 목록을 반환한다.
            return ResponseEntity.ok()
                    .body(allMails);
        }catch(ExpiredJwtException ex){
            //accessToken 만료시 401코드를 반환한다.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }
    @GetMapping("/notread")
    public ResponseEntity<List<MailResponse>> getAllIsNoReadMails(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            String accessToken = tokenService.getAccessTokenFromHeader(authorizationHeader);
            Long instaUserId = tokenService.getInstaUserIdByToken(accessToken);
            User user = userService.findByInstaUserId(instaUserId);

            // 해당 유저의 isRead가 false인 메일만 가져온다.
            List<MailResponse> noReadMails = mailService.findByUser(user)
                    .stream()
                    .filter(mail -> !mail.isRead()) // isRead가 false인 것만 필터링
                    .map(MailResponse::new)
                    .toList();

            // 200 코드와 읽지 않은 메일 목록을 반환한다.
            return ResponseEntity.ok()
                    .body(noReadMails);
        } catch (ExpiredJwtException ex) {
            // accessToken 만료시 401코드를 반환한다.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @Transactional
    @GetMapping("/{mailId}")
    public ResponseEntity<MailResponse> getMail(@PathVariable Long mailId, @RequestHeader("Authorization") String authorizationHeader) {
       try{
           String accessToken = tokenService.getAccessTokenFromHeader(authorizationHeader);
           Long userId = tokenService.getInstaUserIdByToken(accessToken);

           Mail mail = mailService.findById(mailId);
           //메일 열람 정보를 true로 바꾼다.
           mailService.updateIsRead(mailId, true);

           return ResponseEntity.ok()
                   .body(new MailResponse(mail));
       }catch(ExpiredJwtException ex){
        //accessToken 만료시 401코드를 반환한다.
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }



    }

    @DeleteMapping("/{mailId}")
    public ResponseEntity<Void> deleteMail(@PathVariable Long mailId, @RequestHeader("Authorization") String authorizationHeader){
        try {
            String accessToken = tokenService.getAccessTokenFromHeader(authorizationHeader);
            Long instaUserId = tokenService.getInstaUserIdByToken(accessToken);

            Mail mail = mailService.findById(mailId);

            //본인이 메일 수신자인 경우에만 삭제 가능
            if (instaUserId.equals(mail.getUser().getInstaUserId())) {

                mailService.delete(mailId);
                return ResponseEntity.ok()
                        .build();
            }
            //본인이 메일 수신자가 아닌 경우 403코드를 반환한다.
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }catch(ExpiredJwtException ex){
                //accessToken 만료시 401코드를 반환한다.
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/report/{mailId}")
    public ResponseEntity<Void> reportMail(@PathVariable Long mailId, @RequestHeader("Authorization") String authorizationHeader){
        try{
            String accessToken = tokenService.getAccessTokenFromHeader(authorizationHeader);
            Long instaUserId = tokenService.getInstaUserIdByToken(accessToken);

            Mail mail = mailService.findById(mailId);

            //본인이 메일 수신자인 경우에만 신고 가능
            if (instaUserId.equals(mail.getUser().getInstaUserId())) {
                //mail의 정보를 from.anonnn@gmail.com으로 보낸다.
                String to = "from.anonnn@gmail.com";
                String subject = "[편지 신고] Id: " + mailId + " ) 편지 신고가 접수되었습니다.";
                // HTML 형식으로 메일 본문 작성
                String body = "<p><strong>편지 정보</strong></p>"
                        + "<ul>"
                        + "<li>mailId : " + mail.getId() + "</li>"
                        + "<li>userId : " + mail.getUser().getId() + "</li>"
                        + "<li>mailPaperId : " + mail.getMailPaper().getId() + "</li>"
                        + "<li>text : " + mail.getText() + "</li>"
                        + "<li>createdTime : " + mail.getCreatedTime() + "</li>"
                        + "</ul>";

                mailService.sendReportMail(to, subject, body);

                return ResponseEntity.ok()
                        .build();
            }
            //본인이 메일 수신자가 아닌 경우 403코드를 반환한다.
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }catch(ExpiredJwtException ex){
            //accessToken 만료시 401코드를 반환한다.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


    }
}
