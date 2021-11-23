package gruppe1.backend.restcontrollers;

import com.merakianalytics.datapipelines.sources.Get;
import gruppe1.backend.models.Champion;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Champions {



    @GetMapping("/champions")
    public List<Champion> getChampions(){
        return null;
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
