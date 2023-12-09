package fromanon.fromanonserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MailPaper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="MAILPAPER_ID")
    private Long id;

    @Column(name="PRO", nullable = false)
    private boolean pro;

    @Column(name="S3_ADDRESS", nullable = false)
    private String s3Address;

    @Builder
    public MailPaper(boolean pro, String s3Address){
        this.pro = pro;
        this.s3Address = s3Address;
    }
}
