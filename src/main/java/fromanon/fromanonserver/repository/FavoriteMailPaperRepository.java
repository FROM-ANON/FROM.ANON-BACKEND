package fromanon.fromanonserver.repository;

import fromanon.fromanonserver.domain.FavoriteMailPaper;
import fromanon.fromanonserver.domain.Mail;
import fromanon.fromanonserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteMailPaperRepository extends JpaRepository<FavoriteMailPaper, Long> {
    public List<FavoriteMailPaper> findByUser(User user);
}
