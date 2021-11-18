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

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private String userName;

    @Column
    private int level;

//    @ManyToOne
//    @JoinColumn(name = "match_id")
//    @Nullable
//    private Match match;
}


