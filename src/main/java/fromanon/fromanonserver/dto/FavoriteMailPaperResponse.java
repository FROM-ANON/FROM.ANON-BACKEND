package fromanon.fromanonserver.dto;

import fromanon.fromanonserver.domain.MailPaper;
import fromanon.fromanonserver.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteMailPaperResponse {

    private Long id;
    private Long userId;
    private Long mailPaperId;

}
