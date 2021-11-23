package gruppe1.backend.repositories;

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


}
