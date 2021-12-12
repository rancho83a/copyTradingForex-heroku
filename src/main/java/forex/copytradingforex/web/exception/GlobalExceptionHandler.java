package forex.copytradingforex.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({PositionNotFoundException.class})
    public ModelAndView handleDBException(PositionNotFoundException e) {
            ModelAndView modelAndView = new ModelAndView("error/position-not-found");
            modelAndView.addObject("positionId", e.getPositionId());
            modelAndView.setStatus(HttpStatus.NOT_FOUND);
            return modelAndView;
        }

    @ExceptionHandler({ObjectNotFoundException.class})
    public ModelAndView handleDBException(ObjectNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error/object-not-found");
        modelAndView.addObject("objectId", e.getObjectId());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ModelAndView handleDBException(UsernameNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error/username-not-found");
        modelAndView.addObject("username", e.getUsername());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }

    @ExceptionHandler({NotEnoughCapitalException.class})
    public ModelAndView handleDBException(NotEnoughCapitalException e) {

        ModelAndView modelAndView = new ModelAndView("error/warning-not-enough-capital");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
