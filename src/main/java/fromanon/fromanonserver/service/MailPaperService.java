package fromanon.fromanonserver.service;


import fromanon.fromanonserver.domain.MailPaper;
import fromanon.fromanonserver.domain.User;
import fromanon.fromanonserver.repository.MailPaperRepository;
import fromanon.fromanonserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MailPaperService {

    private final MailPaperRepository mailPaperRepository;

    public List<MailPaper> findAll(){
        return mailPaperRepository.findAll();
    }

    public MailPaper findById(Long mailpaperId){
        return mailPaperRepository.findById(mailpaperId)
                .orElseThrow(() -> new IllegalArgumentException("Mailpaper not found with id: " + mailpaperId));
    }
}