package gruppe1.backend.repositories;

import gruppe1.backend.models.Match;
import gruppe1.backend.models.Summoner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class MatchRepositoryImpl implements MatchRepositoryCustom {

    @Autowired
    MatchRepository matchRepository;
    @Autowired
    RiotRepo riotRepo;

    @Override
    public List<Match> findMatchHistoryForSummoner(Summoner summoner) {
        String[] matchIds = riotRepo.getMatchHistory(summoner.getPuuid());
        List<Match> matches = new ArrayList<>();
        for(String matchId : matchIds){
            matches.add(riotRepo.getMatch(matchId,summoner));
        }
        System.out.println("found " + matches.size() + " matches");
        for(Match matchFromRiot : matches){
            matchFromRiot.setSummoner(summoner);

            Match matchFromDb = matchRepository.findByMatchIdAndSummoner_Puuid(matchFromRiot.getMatchId(),summoner.getPuuid());
            if(matchFromDb != null){
                System.out.println("found match in db");
                matchFromRiot.setNote(matchFromDb.getNote());
                matchFromRiot.setId(matchFromDb.getId());
            }
        }

        return matches;
    }

    @Override
    public List<Match> findAllSavedMatches() {
        List<Match> matchesFromDb = matchRepository.findAll();
        List<Match> matchesFromRiot = new ArrayList<>();
        for(Match matchFromDb : matchesFromDb){
            Match matchFromRiot = riotRepo.getMatch(matchFromDb.getMatchId(),matchFromDb.getSummoner());
            matchFromRiot.setNote(matchFromDb.getNote());
            matchFromRiot.setId(matchFromDb.getId());
            matchesFromRiot.add(matchFromRiot);
        }
        return matchesFromRiot;
    }
}
