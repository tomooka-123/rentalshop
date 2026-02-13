package jp.sun.rental.domain.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
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
    
    
    
	@ExceptionHandler(EmptyResultDataAccessException.class)	
	protected String handlerException(EmptyResultDataAccessException e, Model model) {	
		model.addAttribute("error", "該当データがありません。" + e.getMessage());
		e.printStackTrace();
		return "error/error";
	}	
	
	@ExceptionHandler(DataAccessException.class)
	protected String handlerException(DataAccessException e, Model model) {
		model.addAttribute("error", "SQL文でエラーが発生しました。" + e.getMessage());
		e.printStackTrace();
		return "error/error";
	}	
   
	@ExceptionHandler(Exception.class)
	public String handlerException(Exception e, Model model) {
		model.addAttribute("error", "システムエラーが発生しました。" + e.getMessage());
		e.printStackTrace();
		return "error/error";
	}
}