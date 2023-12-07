package fromanon.fromanonserver.service;

import fromanon.fromanonserver.domain.User;
import fromanon.fromanonserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    //instaId로 사용자의 정보를 가져오는 메서드
    @Override
    public User loadUserByUsername(String instaId){
        return userRepository.findByInstaId(instaId)
                .orElseThrow(()->new IllegalArgumentException((instaId)));
    }

}
