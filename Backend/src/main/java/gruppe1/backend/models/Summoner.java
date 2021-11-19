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
    private Long id;

    @Column
    private String name;

    @Column
    private int level;

    @Column
    private String rank;

    @Column
    private int wins;

    @Column
    private int losses;

    @Column
    private boolean friendStatus;


//    @ManyToOne
//    @JoinColumn(name = "match_id")
//    @Nullable
//    private Match match;
}


