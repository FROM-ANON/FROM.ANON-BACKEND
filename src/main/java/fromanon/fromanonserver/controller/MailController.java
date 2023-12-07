package fromanon.fromanonserver.controller;

import fromanon.fromanonserver.domain.Mail;
import fromanon.fromanonserver.dto.MailResponse;
import fromanon.fromanonserver.dto.SendMailRequest;
import fromanon.fromanonserver.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping("/mail")
public class MailController {

    private final MailService mailService;

    @PostMapping
    public ResponseEntity<Mail> sendMail(@RequestBody SendMailRequest request){
        Mail savedMail = mailService.save(request);

        //201코드와 테이블에 저장된 savedMail 객체를 반환한다.
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedMail);
    }

    @GetMapping
    public ResponseEntity<List<MailResponse>> getAllMails() {
//        // 현재 사용자의 Authentication 객체를 가져온다.
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        // Authentication 객체에서 현재 사용자의 정보를 추출한다.
//        Long userId = extractUserIdFromAuthentication(authentication);
//
//        // 현재 사용자의 메일만 가져온다.
//        List<MailResponse> allMails = mailService.getUserinboxMails(userId)
//                .stream()
//                .map(MailResponse::new)
//                .toList();
//
//        // 200 코드와 전체 메일 목록을 반환한다.
//        return ResponseEntity.ok()
//                .body(allMails);


        List<MailResponse> allMails = mailService.findAll()
                .stream()
                .map(MailResponse::new)
                .toList();

        // 200 코드와 전체 메일 목록을 반환한다.
        return ResponseEntity.ok()
                .body(allMails);
    }

    @GetMapping("/{mailId}")
    public ResponseEntity<MailResponse> getMail(@PathVariable Long mailId) {
       Mail mail = mailService.findById(mailId);

        return ResponseEntity.ok()
                .body(new MailResponse(mail));
    }

    @DeleteMapping("/{mailId}")
    public ResponseEntity<Void> deleteMail(@PathVariable Long mailId){
        mailService.delete(mailId);
        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/report/{mailId}")
    public ResponseEntity<Void> reportMail(@PathVariable Long mailId){
        Mail mail = mailService.findById(mailId);

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
}
