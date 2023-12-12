package fromanon.fromanonserver.service;


import fromanon.fromanonserver.domain.User;
import fromanon.fromanonserver.dto.AddUserRequest;
import fromanon.fromanonserver.dto.SearchUserResponse;
import fromanon.fromanonserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService  {

    private final UserRepository userRepository;

    public User findById (Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
    }
    public SearchUserResponse findUserDtoById(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        return mapToSearchUserResponse(user);
    }
    public User findByInstaUserId(Long instaUserId){
        return userRepository.findByInstaUserId(instaUserId)
                .orElseThrow(()-> new IllegalArgumentException("Unexpected user"));
    }
    public User findByInstaId(String instaId){
        return userRepository.findByInstaId(instaId)
                .orElseThrow(()-> new IllegalArgumentException("Unexpected user"));
    }
    public Long saveUser(User user){
        return userRepository.save(user).getId();
    }
    public void deleteUser(User user){
        userRepository.delete(user);
    }
    public List<SearchUserResponse> searchUserByInstaId(String searchWord) {
        List<User> users = userRepository.findByInstaIdContaining(searchWord);
        return users.stream()
                .map(this::mapToSearchUserResponse)
                .collect(Collectors.toList());
    }
    private SearchUserResponse mapToSearchUserResponse(User user) {
        SearchUserResponse response = new SearchUserResponse();
        response.setUserId(user.getId());
        response.setInstaId(user.getInstaId());
        response.setInstaUserId(user.getInstaUserId());
        response.setPro(user.isPro());
        return response;
    }
}