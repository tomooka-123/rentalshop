package jp.sun.rental.presentation.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jp.sun.rental.application.service.CartInsertService;
import jp.sun.rental.application.service.ItemSearchService;
import jp.sun.rental.presentation.form.CartItemForm;
import jp.sun.rental.presentation.form.ItemForm;

@Controller
public class ItemController {

	//フィールド
	private ItemSearchService itemSearchService;
	private CartInsertService cartInsertService;
	
	//コンストラクター
	public ItemController(ItemSearchService itemSearchService, CartInsertService cartInsertService) {
		this.itemSearchService = itemSearchService;
		this.cartInsertService = cartInsertService;
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
			return "itemSearch";
		}else {
			List<ItemForm> formList = itemSearchService.getItemsList(itemForm);
			if (formList != null && !formList.isEmpty()) {
				model.addAttribute("itemFormList", formList);
			}
		}
		
		return "item/search";
	}
	
	
	
	//商品をレンタル希望に追加する
	@PostMapping(value = "/cart/insert")
	public String addCart(@ModelAttribute CartItemForm cartItemForm, Authentication authentication, Model model) throws Exception{
		
		// ログイン中のユーザー名を取得
		String userName = authentication.getName();

		int numberOfRow = cartInsertService.addCart(userName, cartItemForm);
		if(numberOfRow == 0) {
			model.addAttribute("error","レンタル希望の登録に失敗しました。");
			return "error/error";
		}else {
			model.addAttribute("message", "レンタル希望に追加しました。");
		}
		
		return "user/success";
	}
	
	
}
