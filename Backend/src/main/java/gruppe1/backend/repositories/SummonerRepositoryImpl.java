package gruppe1.backend.repositories;

import gruppe1.backend.models.Summoner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

class SummonerRepositoryImpl implements SummonerRepositoryCustom{


    @Autowired
    SummonerRepository summonerRepository;
    @Autowired
    RiotRepo riotRepo;

    @Override
    public Summoner saveSummonerWithName(String summonerName) {
        return riotRepo.findSummonerNamed(summonerName);
    }

    @Override
    public List<Summoner> findAllSummoners() {
        System.out.println("finding all summoners");
        List<Summoner> summoners = summonerRepository.findAll();
        System.out.println("found " + summoners.size() + " summoners");
        return summoners.stream().map(summoner -> riotRepo.addRiotSummonerData(summoner)).collect(Collectors.toList());
    }
}