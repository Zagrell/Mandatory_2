package gruppe1.backend.restcontrollers;

import gruppe1.backend.models.Summoner;
import gruppe1.backend.repositories.SummonerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
public class Summoners {


    @Autowired
    SummonerRepository summonerRepository;

    @GetMapping("/summoners")
    public Iterable<Summoner> getSummoners(){
        return summonerRepository.findAllSummoners();
    }

    @PostMapping("/summoners")
    public Summoner addSummoner(@RequestBody String summonerName){
        try {
            return summonerRepository.saveSummonerWithName(summonerName);
        }catch (NoSuchElementException e){
            e.printStackTrace();
            return null;
        }
    }


    @DeleteMapping("summoners/{puuid}")
    public String deleteSummoner(@PathVariable String puuid){
        summonerRepository.deleteById(puuid);
        return "OK";
    }



}