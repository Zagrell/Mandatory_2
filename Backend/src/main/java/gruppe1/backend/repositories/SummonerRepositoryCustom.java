package gruppe1.backend.repositories;

import gruppe1.backend.models.Summoner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

interface  SummonerRepositoryCustom{


    Summoner saveSummonerWithName(String summonerName);

    List<Summoner> findAllSummoners();

}