package fromanon.fromanonserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USER_ID")
    private Long id;

    @Column(name="INSTA_ID", nullable=false, unique=true)
    private String instaId;

    public void setPro(boolean pro) {
        this.pro = pro;
    }

    @Column(name="PRO", nullable = false)
    @ColumnDefault("false")
    private boolean pro;

    @OneToMany(mappedBy = "user")
    private List<Mail> mails = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<FavoriteMailPaper> favoriteMailPapers = new ArrayList<>();

    @Builder
    public User(String instaId){
        this.instaId = instaId;
        this.pro = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("user"));
    }
    //사용자의 id를 반환
    @Override
    public String getUsername(){
        return instaId;
    }
    //사용자의 password를 반환
    @Override
    public String getPassword(){
        return "";
    }
    //계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired(){
        //만료되었는지 확인하는 로직
        return true; //true -> 만료되지 않았음
    }
    //계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked(){
        //계정 잠금되었는지 확인하는 로직
        return true; //true -> 잠금되지 않았음
    }
    //패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired(){
        //패스워드가 만료되었는지 확인하는 로직
        return true; //true -> 만료되지 않았음
    }
    //계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled(){
        //계정이 사용 가능한지 확인하는 로직
        return true; // true -> 사용 가능
    }
}
