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
    public Summoner findSummonerWithPuuid(String puuid) {
        try{
            Summoner summoner = summonerRepository.findById(puuid).get();
            return riotRepo.addRiotSummonerData(summoner);
        }catch (Exception e){
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public List<Summoner> findAllSummoners() {
        System.out.println("finding all summoners");
        List<Summoner> summoners = summonerRepository.findAll();
        System.out.println("found " + summoners.size() + " summoners");
        return summoners.stream().map(summoner -> riotRepo.addRiotSummonerData(summoner)).collect(Collectors.toList());
    }

    @Override
    public Summoner saveSummonerWithName(String summonerName) {
        Summoner summonerToSave = riotRepo.findSummonerNamed(summonerName);
        summonerRepository.save(summonerToSave);
        return summonerToSave;
    }
}