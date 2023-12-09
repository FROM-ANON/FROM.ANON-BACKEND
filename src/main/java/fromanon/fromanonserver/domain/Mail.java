package fromanon.fromanonserver.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Mail {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="MAIL_ID")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAILPAPER_ID")
    private MailPaper mailPaper;

    @Column(name="TEXT", nullable = false)
    private String text;

    @Column(name="CREATED_TIME", nullable = false)
    private String createdTime;

    private String getLocalDateTime() {
        // 현재 시간을 얻기
        LocalDateTime currentTime = LocalDateTime.now();
        // 출력 형식을 지정하기 위한 DateTimeFormatter 사용 (선택사항)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 현재 시간을 문자열로 변환하여 리턴
        String formattedTime = currentTime.format(formatter);
        return formattedTime;
    }

    @Builder
    public Mail(User user, MailPaper mailPaper, String text){
        this.user = user;
        this.mailPaper = mailPaper;
        this.text = text;
        this.createdTime = getLocalDateTime();
    }

}
