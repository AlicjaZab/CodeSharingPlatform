package platform;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ExceptionController {

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView error(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        model.setViewName("error");
        return model;
    }
}
