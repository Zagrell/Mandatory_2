package gruppe1.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Table(name = "summoners")
@Entity
public class Summoner {

    @Id
    private String puuid;

    @Column
    private String note;

    @JsonIgnore
    @OneToMany(mappedBy = "summoner", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Match> savedMatches;

    @Transient
    private String name;

    @Transient
    private int level;

    @Transient
    private String ranking;

    @Transient
    private int wins;

    @Transient
    private int losses;

    @Transient
    private String profileIconPath;



//    @ManyToOne
//    @JoinColumn(name = "match_id")
//    @Nullable
//    private Match match;
}


