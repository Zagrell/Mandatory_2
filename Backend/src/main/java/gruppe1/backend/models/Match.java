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
    private String outcome;

    @Column
    private LocalDate matchDate;

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
