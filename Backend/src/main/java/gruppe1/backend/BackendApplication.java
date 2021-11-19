package gruppe1.backend;


import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Queue;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.league.League;
import com.merakianalytics.orianna.types.core.staticdata.Champion;
import com.merakianalytics.orianna.types.core.staticdata.Champions;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import gruppe1.backend.repositories.RiotRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@SpringBootApplication
public class BackendApplication {

    private static String apiKey;
    private static RiotRepo repo;

    @Autowired
    public void setApiKey(@Value("${api-key}") String apiKey) {
        BackendApplication.apiKey = apiKey;
    }

    @Autowired
    public void setRepo(RiotRepo repo){
        BackendApplication.repo = repo;
    }


    public static void main(String[] args) throws IOException {
        SpringApplication.run(BackendApplication.class, args);

        Orianna.setRiotAPIKey(apiKey);
        Orianna.setDefaultRegion(Region.EUROPE_WEST);

        Summoner summoner = Orianna.summonerNamed("Pattemand").get();

        Champions champions = Orianna.getChampions();
        Champion randomChampion = champions.get((int) (Math.random() * champions.size()));

        League challengerLeague = Orianna.challengerLeagueInQueue(Queue.RANKED_SOLO).get();
        Summoner bestEUW = challengerLeague.get(0).getSummoner();

        String[] matchHistory = repo.getMatchHistory(summoner.getPuuid());
        for(String matchId: matchHistory){
            repo.getMatch(matchId);
        }


    }


}

