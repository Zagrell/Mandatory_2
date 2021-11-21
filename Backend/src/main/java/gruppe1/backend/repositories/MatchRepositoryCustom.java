package gruppe1.backend.repositories;

import gruppe1.backend.models.Match;

import java.util.List;

public interface MatchRepositoryCustom {

    public List<Match> findMatchHistoryForSummonerWithPuuid(String puuid);

}
