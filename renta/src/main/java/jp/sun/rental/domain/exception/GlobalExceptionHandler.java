package jp.sun.rental.domain.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleBadRequest(IllegalArgumentException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error/error";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleNotFound(UserNotFoundException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error/error";
    }
}