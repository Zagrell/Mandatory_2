package gruppe1.backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class InfoDTO {

    private String gameMode;
    private long gameCreation; // unix timestamp
    private long gameDuration; // seconds
    private List<ParticipantDTO> participants;


}
