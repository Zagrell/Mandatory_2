package gruppe1.backend.repositories;

import gruppe1.backend.models.Match;
import gruppe1.backend.models.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long>, MatchRepositoryCustom {

    Match findByMatchIdAndSummoner_Puuid(String matchId, String summoner_puuid);
}
