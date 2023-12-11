package fromanon.fromanonserver.service;

import fromanon.fromanonserver.domain.FavoriteMailPaper;
import fromanon.fromanonserver.domain.Mail;
import fromanon.fromanonserver.domain.MailPaper;
import fromanon.fromanonserver.domain.User;
import fromanon.fromanonserver.dto.FavoriteMailPaperResponse;
import fromanon.fromanonserver.repository.FavoriteMailPaperRepository;
import fromanon.fromanonserver.repository.MailPaperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FavoriteMailPaperService {

    private final FavoriteMailPaperRepository favoriteMailPaperRepository;

    public List<FavoriteMailPaperResponse> findAll(){
        List<FavoriteMailPaper> favoriteMailPaperList = favoriteMailPaperRepository.findAll();
        return favoriteMailPaperList.stream()
                .map(this::mapToFavoriteMailPaperResponse)
                .collect(Collectors.toList());
    }
    public List<FavoriteMailPaperResponse> findAllByUser(User user){
        List<FavoriteMailPaper> favoriteMailPaperList = favoriteMailPaperRepository.findByUser(user);
        return favoriteMailPaperList.stream()
                .map(this::mapToFavoriteMailPaperResponse)
                .collect(Collectors.toList());
    }

    public FavoriteMailPaperResponse findById(Long favoriteMailpaperId){
        FavoriteMailPaper favoriteMailPaper = favoriteMailPaperRepository.findById(favoriteMailpaperId)
                .orElseThrow(() -> new IllegalArgumentException("FavoriteMailpaper not found with id: " + favoriteMailpaperId));
        return mapToFavoriteMailPaperResponse(favoriteMailPaper);
    }

    private FavoriteMailPaperResponse mapToFavoriteMailPaperResponse(FavoriteMailPaper favoriteMailPaper){
        FavoriteMailPaperResponse response = new FavoriteMailPaperResponse();
        response.setId(favoriteMailPaper.getId());
        response.setUserId(favoriteMailPaper.getUser().getId());
        response.setMailPaperId(favoriteMailPaper.getMailPaper().getId());

        return response;
    }

    public void addFMP(MailPaper mailPaper, User user){
        FavoriteMailPaper favoriteMailPaper = FavoriteMailPaper.builder()
                .mailPaper(mailPaper)
                .user(user)
                .build();
        favoriteMailPaperRepository.save(favoriteMailPaper);
    }
    public void deleteFMP(FavoriteMailPaper favoriteMailPaper){
        favoriteMailPaperRepository.delete(favoriteMailPaper);
    }
    public void deleteFMPById(Long favoriteMailPaperId){
        FavoriteMailPaper favoriteMailPaper = favoriteMailPaperRepository.findById(favoriteMailPaperId)
                .orElseThrow(()-> new IllegalArgumentException("Unexpected favoriteMailPaper"));
        favoriteMailPaperRepository.delete(favoriteMailPaper);
    }
}