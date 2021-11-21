package gruppe1.backend.repositories;

import gruppe1.backend.models.Summoner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

interface  SummonerRepositoryCustom{


    List<Summoner> findAllSummoners();

    Summoner findSummonerWithPuuid(String puuid);

    Summoner saveSummonerWithName(String summonerName);

}