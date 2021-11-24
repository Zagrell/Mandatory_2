package gruppe1.backend.restcontrollers;

import gruppe1.backend.models.Summoner;
import gruppe1.backend.repositories.SummonerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
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
    public Summoner addSummoner(@RequestBody Summoner summoner){
        try {
            return summonerRepository.saveSummonerWithName(summoner.getName());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no summoner with that name", e);
        }
    }

    @DeleteMapping("summoners/{puuid}")
    public String deleteSummoner(@PathVariable String puuid){
        summonerRepository.deleteById(puuid);
        return "OK";
    }

    @PatchMapping("summoners/{puuid}")
    public String editSummoner(@PathVariable String puuid, @RequestBody Summoner editedSummoner){
        return summonerRepository.findById(puuid).map(foundSummoner -> {
            foundSummoner.setNote(editedSummoner.getNote());
            summonerRepository.save(foundSummoner);
            return "summoner updated";
        }).orElse("summoner not found");
    }



}