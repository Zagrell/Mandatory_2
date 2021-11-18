package gruppe1.backend.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "champions")
@Entity
public class Champion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String role;




}
