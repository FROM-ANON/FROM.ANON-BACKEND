package fromanon.fromanonserver.repository;

import fromanon.fromanonserver.domain.Mail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MailRepository extends JpaRepository<Mail, Long> {

    public List<Mail> findByUserId(Long userId);

}