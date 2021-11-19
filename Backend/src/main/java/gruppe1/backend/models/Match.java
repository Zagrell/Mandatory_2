package gruppe1.backend.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Table(name = "matches")
@Entity
public class Match {

    @Id
    @Column
    private String id; // same as riot match id

    @Column
    private String perspective; // puuid of the summoner whose match history this match is from
    //det her vi kan putte many to many og linke til vores summoner klasse

    @Column
    private boolean win;

    @Column
    private long matchStart; //unix timestamp

    @Column
    private long duration; // duration in seconds

    @Column
    private int kills;

    @Column
    private int deaths;

    @Column
    private int assists;

    @Column
    private String gameMode;

    @Column
    private String championName;

//    @OneToMany
//    @JoinColumn (name = "summoner_id")
//    @Nullable
//    private Summoner summoner;
//
//    @OneToMany
//    @JoinColumn (name = "champion_id")
//    @Nullable
//    private Champion champion;

}
