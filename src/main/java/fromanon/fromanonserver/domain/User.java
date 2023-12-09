package fromanon.fromanonserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User  {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USER_ID")
    private Long id;

    @Column(name="INSTA_USER_ID", nullable = false, unique = true)
    private Long instaUserId;

    @Column(name="INSTA_ID", nullable=false)
    private String instaId;

    public void setPro(boolean pro) {
        this.pro = pro;
    }

    @Column(name="PRO", nullable = false)
    @ColumnDefault("false")
    private boolean pro;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Mail> mails = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FavoriteMailPaper> favoriteMailPapers = new ArrayList<>();

    @Builder
    public User(Long instaUserId, String instaId){
        this.instaUserId = instaUserId;
        this.instaId = instaId;
        this.pro = false;
    }

    //사용자 instaId 변경
    public User update(String instaId){
        this.instaId = instaId;
        return this;
    }
}
