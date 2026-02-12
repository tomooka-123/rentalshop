package jp.sun.rental.presentation.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.sun.rental.application.service.RentalService;
import jp.sun.rental.presentation.form.RentalHistoryForm;

@Controller
public class RentalController {

	private RentalService rentalService;
	
	public RentalController(RentalService rentalService) {
		this.rentalService = rentalService;
	}
	
	
	@GetMapping(value = "/history")
	public String toHistory(Model model, Principal principal) throws Exception{
		
		List<RentalHistoryForm> historyFormList = rentalService.getHistoryListByUserName(principal.getName());
		
		model.addAttribute("historyFormList", historyFormList);
		
		return "history";
	}
}
