package gruppe1.backend.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Table(name = "champions")
@Entity
public class Champion {

    @Id
    @Column
    private String name;

    @Column
    private String note;

    @Transient
    private String title;

    @Transient
    private String squareImagePath;

    @Transient
    private List<String> tags;

    @Transient
    private String lore;




}
