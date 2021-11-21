package gruppe1.backend.repositories;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import gruppe1.backend.dto.MatchDTO;
import gruppe1.backend.dto.ParticipantDTO;
import gruppe1.backend.models.Match;
import gruppe1.backend.models.Summoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.NoSuchElementException;


@Repository
public class RiotRepo {

    /**
     * since riot has recently changed a lot of their api. No available java api fully works anymore,
     * we use the orianna api since it seems to have the most functionality intact.
     * But for everything concerning matches we made the http requests ourselves
     */


    private String apiKey;
    private String host;

    @Autowired
    public RiotRepo(@Value("${api-key}") String apiKey) {
        this.apiKey = apiKey;
        host = "https://europe.api.riotgames.com";
        Orianna.setRiotAPIKey(apiKey);
        Orianna.setDefaultRegion(Region.EUROPE_WEST);
    }

    public Summoner addRiotSummonerData(Summoner summoner) {

        com.merakianalytics.orianna.types.core.summoner.Summoner oriannaSummoner = Orianna.summonerWithPuuid(summoner.getPuuid()).get();
        System.out.println(oriannaSummoner);
        summoner.setName(oriannaSummoner.getName());
        summoner.setLevel(oriannaSummoner.getLevel());

        return summoner;
    }

    public Summoner findSummonerNamed(String name) {

        try {
            com.merakianalytics.orianna.types.core.summoner.Summoner oriannaSummoner = Orianna.summonerNamed(name).get();
            Summoner ourSummoner = new Summoner();
            ourSummoner.setPuuid(oriannaSummoner.getPuuid());
            return addRiotSummonerData(ourSummoner);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("no summoner with that name");
        }
    }


    public String[] getMatchHistory(String summonerPuuid) {
        try {

            URL url = new URL(host + "/lol/match/v5/matches/by-puuid/" + summonerPuuid + "/ids");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("X-Riot-Token", apiKey);
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String responseString = response.toString();
            responseString = responseString.replace("[", "");
            responseString = responseString.replace("]", "");
            responseString = responseString.replace("\"", "");

            return responseString.split(",");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Match getMatch(String matchId, Summoner summoner) {
        try {

            URL url = new URL(host + "/lol/match/v5/matches/" + matchId);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("X-Riot-Token", apiKey);
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            MatchDTO matchDTO = mapper.readValue(response.toString(), MatchDTO.class);

            ParticipantDTO participant = matchDTO.getInfo().getParticipants().stream()
                    .filter(participantDTO -> participantDTO.getPuuid().equals(summoner.getPuuid()))
                    .findFirst().get();

            Match matchToReturn = new Match();

            matchToReturn.setMatchId(matchId);
            matchToReturn.setSummoner(summoner);
            matchToReturn.setWin(participant.isWin());
            matchToReturn.setMatchStart(matchDTO.getInfo().getGameCreation());
            matchToReturn.setDuration(matchDTO.getInfo().getGameDuration());
            matchToReturn.setKills(participant.getKills());
            matchToReturn.setDeaths(participant.getDeaths());
            matchToReturn.setAssists(participant.getAssists());
            matchToReturn.setChampionName(participant.getChampionName());
            matchToReturn.setGameMode(matchDTO.getInfo().getGameMode());

            return matchToReturn;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
