package fromanon.fromanonserver.service;


import fromanon.fromanonserver.domain.MailPaper;
import fromanon.fromanonserver.domain.User;
import fromanon.fromanonserver.repository.MailPaperRepository;
import fromanon.fromanonserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MailPaperService {

    private final MailPaperRepository mailPaperRepository;

    public MailPaper getMailPaperById(Long mailPaperId){
        return mailPaperRepository.getReferenceById(mailPaperId);
    }
}