package gruppe1.backend.repositories;

import gruppe1.backend.models.Champion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChampionRepository extends JpaRepository<Champion, String>,ChampionRepositoryCustom {



}
