package jp.sun.rental.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RentalController {

	
	
	@GetMapping(value = "/history")
	public String toHistory(Model model) {
		
		return "history";
	}
}
