package fromanon.fromanonserver.service;

import fromanon.fromanonserver.domain.Mail;
import fromanon.fromanonserver.domain.MailPaper;
import fromanon.fromanonserver.domain.User;
import fromanon.fromanonserver.dto.SendMailRequest;
import fromanon.fromanonserver.repository.MailPaperRepository;
import fromanon.fromanonserver.repository.MailRepository;
import fromanon.fromanonserver.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MailService {

    private final UserRepository userRepository;
    private final MailPaperRepository mailPaperRepository;
    private final MailRepository mailRepository;
    private final JavaMailSender javaMailSender;


    public Mail toEntity(SendMailRequest request){
        //userId, mailPaperId를 사용해 user, mailPaper 엔티티를 가져온다.
        User user = userRepository.getReferenceById(request.getUserId());
        MailPaper mailPaper = mailPaperRepository.getReferenceById(request.getMailPaperId());
        //Mail 객체를 만들어 리턴한다.
        return Mail.builder()
                .user(user)
                .mailPaper(mailPaper)
                .text(request.getText())
                .build();
    }

    //편지 작성 메서드
    public Mail save(SendMailRequest request){
        return mailRepository.save(toEntity(request));
    }
    //전체 편지 조회 메서드
    public List<Mail> findAll() {
        return mailRepository.findAll();
    }
    //해당 사용자의 편지 전체 조회 메서드
    public List<Mail> findByUser(User user) {
        return mailRepository.findByUser(user);
    }
    //편지 아이디로 조회 메서드
    public Mail findById(Long mailId) {
        return mailRepository.findById(mailId)
                .orElseThrow(()->new IllegalArgumentException(("not found: " + mailId)));
    }
    //편지 삭제 메서드
    public void delete(Long mailId){
        mailRepository.deleteById(mailId);
    }
    //편지 신고 메일 전송 메서드
    public void sendReportMail(String to, String subject, String body) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(to); // 메일 수신자
            mimeMessageHelper.setSubject(subject); // 메일 제목
            mimeMessageHelper.setText(body, true); // 메일 본문 내용, HTML 여부
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    //메일 열람 정보 update 메서드
    public void updateIsRead(Long mailId, Boolean isRead){
        mailRepository.updateIsRead(mailId, isRead);
    }

}
