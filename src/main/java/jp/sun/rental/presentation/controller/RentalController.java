package jp.sun.rental.presentation.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
		
		return "user/history";
	}
	
	@PostMapping(value = "/history/return")
	public String changeReturn(@RequestParam String rentalItemId, Model model) throws Exception{
		int numRow = 0;
		
		numRow = rentalService.changeReturnFlag(rentalItemId);
		
		if(numRow < 1) {
			model.addAttribute("error","返却情報の更新に失敗しました。");
			return "error/error";
		}else {
			return "redirect:/history";
		}
	}
	
	@PostMapping(value = "/history/delete")
	public String deleteHistory(@RequestParam String rentalItemId, Model model) throws Exception{
		int numRow = 0;
		
		numRow = rentalService.changeDeleteFlag(rentalItemId);
		
		if(numRow < 1) {
			model.addAttribute("error","削除に失敗しました。");
			return "error/error";
		}else {
			return "redirect:/history";
		}
		
	}
}
