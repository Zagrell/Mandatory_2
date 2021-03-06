package gruppe1.backend.repositories;

import gruppe1.backend.models.Match;
import gruppe1.backend.models.Summoner;

import java.util.List;

public interface MatchRepositoryCustom {

    public List<Match> findMatchHistoryForSummoner(Summoner summoner);

    public List<Match> findAllSavedMatches();

}
