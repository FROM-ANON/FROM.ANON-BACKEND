package fromanon.fromanonserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FavoriteMailPaper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="FMP_ID")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAILPAPER_ID")
    private MailPaper mailPaper;

    @Builder
    public FavoriteMailPaper(MailPaper mailPaper, User user){
        this.mailPaper = mailPaper;
        this.user = user;
    }


}
