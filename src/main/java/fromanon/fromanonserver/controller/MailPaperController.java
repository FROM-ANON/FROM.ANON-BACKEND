package fromanon.fromanonserver.controller;

import fromanon.fromanonserver.domain.MailPaper;
import fromanon.fromanonserver.repository.MailPaperRepository;
import fromanon.fromanonserver.service.MailPaperService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/mailpaper")
public class MailPaperController {
    private final MailPaperService mailPaperService;

    @GetMapping
    public ResponseEntity<List<MailPaper>> getAllMailpapers(){
        List<MailPaper> mailPaperList = mailPaperService.findAll();
        return ResponseEntity.ok().body(mailPaperList);
    }

    @GetMapping("/{mailpaperId}")
    public ResponseEntity<MailPaper> getMailpaper(@PathVariable Long mailpaperId){
        MailPaper mailPaper = mailPaperService.findById(mailpaperId);
        return ResponseEntity.ok().body(mailPaper);
    }
}
