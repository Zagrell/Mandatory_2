package gruppe1.backend.repositories;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import gruppe1.backend.BackendApplication;
import gruppe1.backend.dto.*;
import gruppe1.backend.models.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.stream.Collectors;


@Repository
public class RiotRepo {

    /**
     * since riot has recently changed a lot of their api. No available java api fully works anymore,
     * we use the orianna api since it seems to have the most functionality intact.
     * But for everything concerning matches we made the http requests ourselves
     * */




    private String apiKey;
    private String host;
    private String currentSummoner;

    @Autowired
    public RiotRepo(@Value("${api-key}") String apiKey){
        this.apiKey = apiKey;
        host = "https://europe.api.riotgames.com";
        Orianna.setRiotAPIKey(apiKey);
        Orianna.setDefaultRegion(Region.EUROPE_WEST);
    }

    public Summoner getSummonerByName(String name){

            com.merakianalytics.orianna.types.core.summoner.Summoner oriannaSummoner = Orianna.summonerNamed(name).get();
            Summoner ourSummoner = new Summoner();
            ourSummoner.setName(oriannaSummoner.getName());
            ourSummoner.setLevel(oriannaSummoner.getLevel());
            currentSummoner = oriannaSummoner.getPuuid();
            return ourSummoner;
    }

    public String[] getMatchHistory(String summonerPuuid) throws IOException {

        currentSummoner = summonerPuuid;

        URL url = new URL(host + "/lol/match/v5/matches/by-puuid/" + summonerPuuid + "/ids");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("X-Riot-Token",apiKey);
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
        responseString = responseString.replace("[","");
        responseString = responseString.replace("]","");
        responseString = responseString.replace("\"","");

        return responseString.split(",");
    }

    public  Match getMatch(String matchId) throws IOException {
        return getMatch(matchId,currentSummoner);
    }

    public Match getMatch(String matchId, String summonerPuuid) throws IOException {
        URL url = new URL(host + "/lol/match/v5/matches/" + matchId);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("X-Riot-Token",apiKey);
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
        MatchDTO matchDTO = mapper.readValue(response.toString(),MatchDTO.class);

        ParticipantDTO participant = matchDTO.getInfo().getParticipants().stream()
                .filter(participantDTO -> participantDTO.getPuuid().equals(summonerPuuid))
                .findFirst().get();

        Match matchToReturn = new Match();

        matchToReturn.setId(matchId);
        matchToReturn.setPerspective(summonerPuuid);
        matchToReturn.setWin(participant.isWin());
        matchToReturn.setMatchStart(matchDTO.getInfo().getGameCreation());
        matchToReturn.setDuration(matchDTO.getInfo().getGameDuration());
        matchToReturn.setKills(participant.getKills());
        matchToReturn.setDeaths(participant.getDeaths());
        matchToReturn.setAssists(participant.getAssists());
        matchToReturn.setChampionName(participant.getChampionName());
        matchToReturn.setGameMode(matchDTO.getInfo().getGameMode());

        return matchToReturn;

    }







}
