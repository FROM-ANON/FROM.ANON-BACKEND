package fromanon.fromanonserver.repository;

import fromanon.fromanonserver.domain.Mail;
import fromanon.fromanonserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MailRepository extends JpaRepository<Mail, Long> {

    public List<Mail> findByUser(User user);

    @Modifying
    @Query("UPDATE Mail m SET m.isRead = :isRead WHERE m.id = :mailId")
    public void updateIsRead(@Param("mailId") Long mailId, @Param("isRead") Boolean isRead);
}
