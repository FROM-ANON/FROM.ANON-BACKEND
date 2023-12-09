package fromanon.fromanonserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TempUser  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TEMP_USER_ID")
    private Long id;

    @Column(name="INSTA_USER_ID", nullable = false, unique = true)
    private Long instaUserId;

    @Column(name="INSTA_ID", nullable=false)
    private String instaId;

    @Builder
    public TempUser(Long instaUserId, String instaId){
        this.instaUserId = instaUserId;
        this.instaId = instaId;
    }
}
