package fromanon.fromanonserver.repository;

import fromanon.fromanonserver.domain.TempUser;
import fromanon.fromanonserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TempUserRepository extends JpaRepository<TempUser, Long> {
    Optional<TempUser> findByInstaId(String instaId);
    Optional<TempUser> findByInstaUserId(Long instaUserId);
}