package gruppe1.backend;


import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Queue;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.league.League;
import com.merakianalytics.orianna.types.core.staticdata.Champion;
import com.merakianalytics.orianna.types.core.staticdata.Champions;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);

        Orianna.setRiotAPIKey("RGAPI-0255eb63-b60e-49f0-93cb-a50997b8ad5c");
        Orianna.setDefaultRegion(Region.EUROPE_WEST);

        Summoner summoner = Orianna.summonerNamed("Pattemand").get();
        System.out.println(summoner.getName() + " is level " + summoner.getLevel() + " on the " + summoner.getRegion() + " server.");

        Champions champions = Orianna.getChampions();
        Champion randomChampion = champions.get((int) (Math.random() * champions.size()));
        System.out.println("He enjoys playing champions such as " + randomChampion.getName());

        League challengerLeague = Orianna.challengerLeagueInQueue(Queue.RANKED_SOLO).get();
        Summoner bestEUW = challengerLeague.get(0).getSummoner();
        System.out.println("He's not as good as " + bestEUW.getName() + " at League, but probably a better Java programmer!");






    }



}

