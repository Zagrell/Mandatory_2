package gruppe1.backend.repositories;

import gruppe1.backend.models.Summoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SummonerRepository extends SummonerRepositoryCustom,SummonerRepositoryBasic{


}