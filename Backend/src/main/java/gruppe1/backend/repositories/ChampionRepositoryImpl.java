package gruppe1.backend.repositories;

import gruppe1.backend.models.Champion;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ChampionRepositoryImpl implements ChampionRepositoryCustom{

    @Autowired
    RiotRepo riotRepo;

    @Autowired
    ChampionRepository championRepository;

    @Override
    public List<Champion> findAllChampions() {
        return riotRepo.findAllChampionImages();
    }

    @Override
    public Champion findChampionsWithName(String name) {
        try {

            Optional<Champion> optionalChampion = championRepository.findById(name);
            Champion champion;

            if(optionalChampion.isPresent()){
                champion = optionalChampion.get();
            }else{
                champion = new Champion();
                champion.setName(name);
            }
            return riotRepo.addRiotChampionData(champion);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
