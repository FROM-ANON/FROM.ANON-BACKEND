package fromanon.fromanonserver.dto;

import fromanon.fromanonserver.domain.Mail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MailResponse{

    private Long mailId;
    private String text;
    private String createdTime;

    public MailResponse(Mail mail){
        this.mailId = mail.getId();
        this.text = mail.getText();
        this.createdTime = mail.getCreatedTime();
    }
}
