package org.example.warehouse.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class DefaultAdvice {

    @ExceptionHandler(OperationException.class)
    public ModelAndView handleOperationException(OperationException exception){
        String message = exception.getMessage();
        return new ModelAndView("errorOperationPage"
                , "message", message);
    }
}
