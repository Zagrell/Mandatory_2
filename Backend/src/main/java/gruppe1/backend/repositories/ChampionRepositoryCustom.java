package gruppe1.backend.repositories;

import gruppe1.backend.models.Champion;

import java.util.List;

public interface ChampionRepositoryCustom {


    List<String> findAllChampions();

    Champion findChampionsWithName(String name);

}
