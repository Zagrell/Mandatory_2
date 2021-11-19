package gruppe1.backend.controllers;

import gruppe1.backend.models.Summoner;
import gruppe1.backend.repositories.SummonerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Summoners {


    @Autowired
    SummonerRepository summonerRepository;

    @GetMapping("/summoners")
    public Iterable<Summoner> getSummoners(){
        System.out.println("heya");
        return summonerRepository.findAll();
    }

    @PostMapping("/summoners/{summonerName}")
    public Summoner addSummoner(@PathVariable String name){
        System.out.println("ugg");
        return summonerRepository.save(name);

    }

}