package fromanon.fromanonserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SendMailRequest {

    private Long userId;
    private Long mailPaperId;
    private String text;
    private Boolean isRead;

    public void setRead(Boolean read) {
        isRead = read;
    }
}
