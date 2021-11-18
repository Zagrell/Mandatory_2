package gruppe1.backend.repositories;

import com.fasterxml.jackson.core.JsonParser;
import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import gruppe1.backend.BackendApplication;
import gruppe1.backend.models.Match;
import gruppe1.backend.models.Summoner;
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


@Repository
public class RiotRepo {

    private String apiKey;
    private String host;

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
            ourSummoner.setUserName(oriannaSummoner.getName());
            ourSummoner.setLevel(oriannaSummoner.getLevel());

            return ourSummoner;
    }

    public String[] getMatchHistory(String summonerPuuid) throws IOException {
        URL url = new URL(host + "/lol/match/v5/matches/by-puuid/" + summonerPuuid + "/ids");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("X-Riot-Token",apiKey);
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String responseString = response.toString();
        responseString = responseString.replace("[","");
        responseString = responseString.replace("]","");
        responseString = responseString.replace("\"","");
        String[] stringArrayToReturn = responseString.split(",");

        return stringArrayToReturn;
    }

    public Match getMatch(String matchId) throws IOException {
        URL url = new URL(host + "/lol/match/v5/matches/" + matchId);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("X-Riot-Token",apiKey);
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();



        return null;

    }







}
