package gruppe1.backend.dto;

import lombok.Data;

@Data
public class ParticipantDTO {

    private String puuid;
    private boolean win;
    private String summonerName;
    private int kills;
    private int deaths;
    private int assists;
    private String championName;

}
