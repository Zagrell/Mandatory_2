package gruppe1.backend.repositories;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.staticdata.Champions;
import gruppe1.backend.dto.MatchDTO;
import gruppe1.backend.dto.ParticipantDTO;
import gruppe1.backend.models.Champion;
import gruppe1.backend.models.Match;
import gruppe1.backend.models.Summoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Repository
public class RiotRepo {

    /**
     * since riot has recently changed a lot of their api. No available java api fully works anymore,
     * we use the orianna api since it seems to have the most functionality intact.
     * But for everything concerning matches we made the http requests ourselves
     */


    private final String apiKey;
    private final String host;
    private static final String BASE_IMAGE_PATH = "http://ddragon.leagueoflegends.com/cdn/11.23.1/img/";
    private static final String CHAMPION_IMAGE_PATH = "champion/";
    private static final String PROFILE_ICON_PATH = "profileicon/";

    @Autowired
    public RiotRepo(@Value("${api-key}") String apiKey) {
        this.apiKey = apiKey;
        host = "https://europe.api.riotgames.com";
        Orianna.setRiotAPIKey(apiKey);
        Orianna.setDefaultRegion(Region.EUROPE_WEST);
    }

    public Summoner findSummonerNamed(String name) throws IllegalStateException  {
            com.merakianalytics.orianna.types.core.summoner.Summoner oriannaSummoner = Orianna.summonerNamed(name).get();
            Summoner ourSummoner = new Summoner();
            ourSummoner.setPuuid(oriannaSummoner.getPuuid());
            return addRiotSummonerData(ourSummoner);

    }

    public Summoner addRiotSummonerData(Summoner summoner) {

        com.merakianalytics.orianna.types.core.summoner.Summoner oriannaSummoner = Orianna.summonerWithPuuid(summoner.getPuuid()).get();
        summoner.setName(oriannaSummoner.getName());
        summoner.setLevel(oriannaSummoner.getLevel());
        summoner.setProfileIconPath(BASE_IMAGE_PATH + PROFILE_ICON_PATH + oriannaSummoner.getProfileIcon().getImage().getFull());
        return summoner;
    }

    public List<Champion> findAllChampionImages() {
        Champions oriannaChampions = Orianna.getChampions();
        List<Champion> championImageList = new ArrayList<>();

        oriannaChampions.forEach(oriannaChampion -> {
            Champion champion = new Champion();
            champion.setSquareImagePath(BASE_IMAGE_PATH + CHAMPION_IMAGE_PATH + oriannaChampion.getImage().getFull());
            champion.setName(oriannaChampion.getName());
            championImageList.add(champion);
        });
        return championImageList;
    }

    public List<Champion> findAllChampions() {
        Champions oriannaChampions = Orianna.getChampions();
        List<Champion> ourChampions = new ArrayList<>();
        oriannaChampions.forEach(oriannaChampion -> {
            Champion ourChampion = new Champion();
        });
        return null;
    }

    public Champion findChampionNamed(String championName) throws IllegalStateException {
        try {
            com.merakianalytics.orianna.types.core.staticdata.Champion oriannaChampion = Orianna.championNamed(championName).get();
            Champion ourChampion = new Champion();
            ourChampion.setName(championName);
            return addRiotChampionData(ourChampion);
        } catch (IllegalStateException e) {
            throw new IllegalStateException("no champion with that name");
        }
    }

    public Champion addRiotChampionData(Champion champion) {
        com.merakianalytics.orianna.types.core.staticdata.Champion oriannaChampion = Orianna.championNamed(champion.getName()).get();
        champion.setTitle(oriannaChampion.getTitle());
        champion.setTags(oriannaChampion.getTags());
        champion.setLore(oriannaChampion.getLore());
        champion.setSquareImagePath(BASE_IMAGE_PATH + CHAMPION_IMAGE_PATH + oriannaChampion.getImage().getFull());
        System.out.println(champion);
        return champion;
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
