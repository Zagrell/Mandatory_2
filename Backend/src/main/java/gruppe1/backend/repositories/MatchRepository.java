package gruppe1.backend.repositories;

import gruppe1.backend.models.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long>, MatchRepositoryCustom {

}
