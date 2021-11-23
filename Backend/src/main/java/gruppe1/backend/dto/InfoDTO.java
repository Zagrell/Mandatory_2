package gruppe1.backend.dto;

import com.merakianalytics.orianna.types.dto.match.Participant;
import lombok.Data;

import java.util.List;

@Data
public class InfoDTO {


    private long gameCreation;
    private long gameDuration;
    private long gameId;
    private String gameMode;
    private String gameType;
    private String gameVersion;
    private int mapId;

    //private List<ParticipantIdentity> participantIdentities;
    private List<ParticipantDTO> participants;
    private String platformId;
    private int queueId;
    private int seasonId;
   // private List<TeamStats> teams;


}
