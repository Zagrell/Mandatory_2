package gruppe1.backend.repositories;

import gruppe1.backend.models.Champion;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ChampionRepositoryImpl implements ChampionRepositoryCustom{

    @Autowired
    RiotRepo riotRepo;

    @Autowired
    ChampionRepository championRepository;

    @Override
    public List<String> findAllChampions() {
        return riotRepo.findAllChampionImages();
    }

    @Override
    public Champion findChampionsWithName(String name) {
        try {
            Champion champion = championRepository.findById(name).get();
            return riotRepo.addRiotChampionData(champion);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
