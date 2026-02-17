package jp.sun.rental.presentation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.sun.rental.application.service.ItemSearchService;
import jp.sun.rental.presentation.form.ItemForm;

@Controller
public class ItemController {

	//フィールド
	private ItemSearchService itemSearchService;
	
	//コンストラクター
	public ItemController(ItemSearchService itemSearchService) {
		this.itemSearchService = itemSearchService;
	}
	
	
	@GetMapping(value = "/search/item")
	public String toItemSearch(Model model) {
		
		ItemForm itemForm = new ItemForm();
		
		model.addAttribute("itemForm", itemForm);
		
		return "item/search";
	}
	
	@PostMapping(value = "/search/item")
	public String searchUsers(@ModelAttribute ItemForm itemForm, BindingResult result, Model model) throws Exception {
		if (result.hasErrors()) {
			return "item/search";
		}else {
			List<ItemForm> formList = itemSearchService.getItemsList(itemForm);
			if (formList != null && !formList.isEmpty()) {
				model.addAttribute("itemFormList", formList);
			}
		}
		
		return "item/search";
	}
	
	
	
	//商品の「詳細を見る」
	@GetMapping(value = "/item/detail")
	public String itemDetail(@RequestParam("itemId") String itemId, Model model) {
		Integer id;
		try {
			id = Integer.parseInt(itemId);
		}catch(NumberFormatException e){
			model.addAttribute("error", "不正な値が入力されました。");
			e.printStackTrace();
			return "error/error";
		}
		ItemForm itemForm = itemSearchService.getItem(id);
		model.addAttribute("itemForm", itemForm);
		
		return "item/detail";
	}
	
}
