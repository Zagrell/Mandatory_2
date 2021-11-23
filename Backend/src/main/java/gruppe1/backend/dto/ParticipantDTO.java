package gruppe1.backend.dto;

import lombok.Data;

@Data
public class ParticipantDTO {

    String puuid;
    private String summonerName;
    boolean win;
    private int kills;
    private int deaths;
    private int assists;
    private String championName;

}
