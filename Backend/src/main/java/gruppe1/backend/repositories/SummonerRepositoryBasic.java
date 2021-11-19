package gruppe1.backend.repositories;

import gruppe1.backend.models.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;

interface SummonerRepositoryBasic extends JpaRepository<Summoner, String> {


}