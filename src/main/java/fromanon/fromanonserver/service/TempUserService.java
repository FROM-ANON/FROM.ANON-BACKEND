package fromanon.fromanonserver.service;


import fromanon.fromanonserver.domain.TempUser;
import fromanon.fromanonserver.domain.User;
import fromanon.fromanonserver.dto.AddUserRequest;
import fromanon.fromanonserver.repository.TempUserRepository;
import fromanon.fromanonserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TempUserService  {

    private final TempUserRepository tempUserRepository;

    public TempUser findById(Long userId){
        return tempUserRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("Unexpected user"));
    }
    public TempUser findByInstaUserId(Long instaUserId){
        return tempUserRepository.findByInstaUserId(instaUserId)
                .orElseThrow(()-> new IllegalArgumentException("Unexpected user"));
    }
    public TempUser findByInstaId(String instaId){
        return tempUserRepository.findByInstaId(instaId)
                .orElseThrow(()-> new IllegalArgumentException("Unexpected user"));
    }
    public Long saveUser(TempUser user){
        return tempUserRepository.save(user).getId();
    }
    public void deleteUser(TempUser user){
        tempUserRepository.delete(user);
    }
}