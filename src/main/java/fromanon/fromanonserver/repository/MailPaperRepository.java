package fromanon.fromanonserver.repository;

import fromanon.fromanonserver.domain.MailPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailPaperRepository extends JpaRepository<MailPaper, Long> {

}
