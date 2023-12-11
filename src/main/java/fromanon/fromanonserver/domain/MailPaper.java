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
    @Builder
    public MailPaper(boolean pro){
        this.pro = pro;
    }
}
