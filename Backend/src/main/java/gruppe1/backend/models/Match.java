package gruppe1.backend.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Table(name = "matches")
@Entity
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String matchId;

    @Column
    private String note;

    @ManyToOne
    @JoinColumn(name="puuid", nullable=false)
    private Summoner summoner;

    @Transient
    private boolean win;

    @Transient
    private long matchStart; //unix timestamp

    @Transient
    private long duration; // duration in seconds

    @Transient
    private int kills;

    @Transient
    private int deaths;

    @Transient
    private int assists;

    @Transient
    private String gameMode;

    @Transient
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
