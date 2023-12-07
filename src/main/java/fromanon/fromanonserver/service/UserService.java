package fromanon.fromanonserver.service;


import fromanon.fromanonserver.domain.User;
import fromanon.fromanonserver.dto.AddUserRequest;
import fromanon.fromanonserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService  {

    private final UserRepository userRepository;

    public User getUserById(Long userId){
        return userRepository.getReferenceById(userId);
    }

    public Long save(AddUserRequest dto){
        return userRepository.save(User.builder()
                .instaId(dto.getInstaId())
                .build()).getId();
    }
}