package jp.sun.rental.presentation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jp.sun.rental.application.service.ItemSearchService;
import jp.sun.rental.presentation.form.ItemForm;

@Controller
public class ItemController {

	private ItemSearchService itemSearchService;
	
	public ItemController(ItemSearchService itemSearchService) {
		this.itemSearchService = itemSearchService;
	}
	
	@GetMapping(value = "/search/item")
	public String toItemSearch(Model model) {
		
		ItemForm itemForm = new ItemForm();
		
		model.addAttribute("itemForm", itemForm);
		
		return "itemSearch";
	}
	
	@PostMapping(value = "/search/item")
	public String searchUsers(@ModelAttribute ItemForm itemForm, BindingResult result, Model model) throws Exception {
		if (result.hasErrors()) {
			return "itemSearch";
		}else {
			List<ItemForm> formList = itemSearchService.getItemsList(itemForm);
			if (formList != null && !formList.isEmpty()) {
				model.addAttribute("itemFormList", formList);
			}
		}
		
		return "itemSearch";
	}
}
