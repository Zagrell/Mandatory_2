package gruppe1.backend.restcontrollers;

import gruppe1.backend.models.Match;
import gruppe1.backend.repositories.MatchRepository;
import gruppe1.backend.repositories.SummonerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Matches {

    @Autowired
    MatchRepository matchRepository;
    @Autowired
    SummonerRepository summonerRepository;


    @GetMapping("/matches")
    public List<Match> getSavedMatches(){
        return matchRepository.findAllSavedMatches();
    }

    @GetMapping("/matches/{summonerPuuid}")
    public List<Match> getSummonerMatchHistory(@PathVariable String summonerPuuid){
        return matchRepository.findMatchHistoryForSummoner(summonerRepository.findSummonerWithPuuid(summonerPuuid));
    }

    @PostMapping("/matches")
    public Match saveMatch(@RequestBody Match newMatch){
        newMatch.setId(null);
        return matchRepository.save(newMatch);
    }

    @DeleteMapping("matches/{id}")
    public String deleteMatch(@PathVariable Long id){
        matchRepository.deleteById(id);
        return "OK";
    }

    @PatchMapping("matches/{id}")
    public String updateMatch(@PathVariable Long id, @RequestBody Match matchToUpdateWith){
        return matchRepository.findById(id).map(foundMatch -> {
            foundMatch.setNote(matchToUpdateWith.getNote());
            matchRepository.save(foundMatch);
            return "match updated";
        }).orElse("match not found");
    }




}
