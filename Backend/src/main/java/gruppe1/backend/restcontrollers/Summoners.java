package gruppe1.backend.restcontrollers;

import gruppe1.backend.models.Summoner;
import gruppe1.backend.repositories.SummonerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Summoners {


    @Autowired
    SummonerRepository summonerRepository;

    @GetMapping("/summoners")
    public Iterable<Summoner> getSummoners(){
        return summonerRepository.findAllSummoners();
    }

    @PostMapping("/summoners/{summonerName}")
    public Summoner addSummoner(@PathVariable String name){
        return summonerRepository.saveSummonerWithName(name);
    }

    @DeleteMapping("summoners/{puuid}")
    public String deleteSummoner(@PathVariable String puuid){
        summonerRepository.deleteById(puuid);
        return "OK";
    }



}