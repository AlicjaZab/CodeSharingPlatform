package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;


@RestController
@RequestMapping("/api/code")
public class ApiController {

    @Autowired
    private CodeRepository codeRepository;


    @GetMapping(path = "/{N}")
    public CodeEntity get_code_json(@PathVariable String N){
        Optional<CodeEntity> code = codeRepository.findById(N);
        if(code.isPresent()){
            if(code.get().isRestrictedViews()){
                if(code.get().getViews() == 0){
                    //delete form database & return nothing
                    codeRepository.delete(code.get());
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
                }else{
                    //decrement number of left views
                    code.get().setViews(code.get().getViews() - 1);
                    codeRepository.save(code.get());
                }
            }if(code.get().getExpDate() != null){
                LocalDateTime now = LocalDateTime.now().withNano(0);
                long leftSeconds = ChronoUnit.SECONDS.between(now, code.get().getExpDate());
                code.get().setTime(leftSeconds);
                if(leftSeconds <= 0){
                    //delete form database & return nothing
                    codeRepository.delete(code.get());
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
                }
            }
            return code.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");

    }

    @PostMapping(path = "/new")
    public ResponseEntity<String> update_code_json(@RequestBody CodeEntity entity){
        if(entity.getViews()<0) entity.setViews(0);
        if(entity.getTime() < 0) entity.setTime(0);
        if(entity.getTime() != 0) entity.setExpDate(entity.getRawDate().plusSeconds(entity.getTime()));
        entity.setRestrictedViews(entity.getViews() == 0 ? false: true);
        String id = codeRepository.save(entity).getId();
        ResponseEntity<String> response = new ResponseEntity<>("{ \"id\" : \"" + id + "\"}", HttpStatus.OK);
        return  response;
    }



    @GetMapping(path = "/latest")
    public List<CodeEntity> getLatestApi(){
        List<CodeEntity> latest = codeRepository.findAllByRestrictedViewsAndTime(false, 0);

        Collections.reverse(latest);
            if(latest.size() >= 10)
                return latest.subList(0, 10);
            else
                return latest;
    }

}
