package gruppe1.backend.restcontrollers;

import com.merakianalytics.datapipelines.sources.Get;
import gruppe1.backend.models.Champion;
import gruppe1.backend.repositories.ChampionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Champions {

    @Autowired
    ChampionRepository championRepository;



    @GetMapping("/champions")
    public List<String> getChampions(){
        return championRepository.findAllChampions();
    }

    @PostMapping("/champions/{championName}")
    public Champion saveChampion(@PathVariable String championName){
        return null;
    }

    @DeleteMapping("/champion/{championName}")
    public Champion deleteChampion(@PathVariable String championName,@RequestBody Champion champion){
        return null;
    }

    @PutMapping("/champions/{championName}")
    public Champion setChampion(@PathVariable String championName,@RequestBody Champion champion){
        return null;
    }


}
