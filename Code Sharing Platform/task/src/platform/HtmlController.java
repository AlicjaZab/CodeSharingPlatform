package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/code")
public class HtmlController {

    @Autowired
    private CodeRepository codeRepository;

    @GetMapping(path = "/new")
    public ModelAndView update_code_html() throws Exception{
        ModelAndView model = new ModelAndView();

        model.setViewName("newCode");

        return model;
    }

    @GetMapping(path = "/{N}")
    public ModelAndView get_code_html(@PathVariable String N) throws Exception{
        ModelAndView model = new ModelAndView();

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
            model.addObject("code", code.get());
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }
        model.setViewName("code");

        return model;
    }

    @GetMapping(path = "/latest")
    public ModelAndView getLatestHtml() throws Exception{
        ModelAndView model = new ModelAndView();
        List<CodeEntity> latest = codeRepository.findAllByRestrictedViewsAndTime(false, 0);


        Collections.reverse(latest);
        if (latest.size() >= 10)
            model.addObject("latest", latest.subList(0, 10));
        else
            model.addObject("latest", latest);


        model.setViewName("getLatest");

        return model;
    }
}
