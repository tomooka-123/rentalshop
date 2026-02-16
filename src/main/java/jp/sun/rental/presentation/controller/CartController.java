package jp.sun.rental.presentation.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jp.sun.rental.application.service.CartDeleteService;
import jp.sun.rental.application.service.CartInsertService;
import jp.sun.rental.application.service.CartService;
import jp.sun.rental.application.service.RentalService;
import jp.sun.rental.presentation.form.CartForm;
import jp.sun.rental.presentation.form.CartItemForm;

@Controller
public class CartController {

	private CartService cartService;
	private CartInsertService cartInsertService;
	private CartDeleteService cartDeleteService;
	private RentalService rentalService;
	
	public CartController(CartService cartService, CartInsertService cartInsertService, CartDeleteService cartDeleteService, RentalService rentalService) {
		this.cartService = cartService;
		this.cartInsertService = cartInsertService;
		this.cartDeleteService = cartDeleteService;
		this.rentalService = rentalService;
	}
	
	//ログイン中のユーザのカート情報を取得して中身表示用のページへ遷移
	@GetMapping(value = "/cart")
	public String toCart(Model model, Authentication authentication) throws Exception{
		
	CartForm cartForm = cartService.getCartByUserName(authentication.getName());
		
	model.addAttribute("cartForm",cartForm);
	
	return "item/cart";
	}
	
	
	
	//商品をレンタル希望に追加する
	@PostMapping(value = "/cart/insert")
	public String addCart(@ModelAttribute CartItemForm cartItemForm, Authentication authentication, Model model) throws Exception{
		
		// ログイン中のユーザー名を取得
		String userName = authentication.getName();
		
		try {
			cartInsertService.addCart(userName, cartItemForm);
		    model.addAttribute("message", "レンタル希望に追加しました。");
		    
		} catch (IllegalStateException e) {
		    model.addAttribute("message", "既に登録されています。");
			
		} catch (Exception e) {
			model.addAttribute("error","レンタル希望の登録に失敗しました。");
			e.printStackTrace();
			return "error/error";
		}
		
		//レンタル希望画面を表示する処理
		CartForm cartForm = cartService.getCartByUserName(authentication.getName());		
		model.addAttribute("cartForm",cartForm);
		return "item/cart";
	}
	
	
	//商品をレンタル希望から削除する
	@PostMapping(value = "/cart/delete")
	public String deleteItem(@ModelAttribute CartItemForm cartItemForm, Authentication authentication, Model model) throws Exception {

		// ログイン中のユーザー名を取得
		String userName = authentication.getName();
		
		int numberOfRow = cartDeleteService.deleteItem(userName, cartItemForm);
		
		if(numberOfRow == 0) {
			model.addAttribute("message", "削除に失敗しました。");
		}else {
			model.addAttribute("message", "レンタル希望から削除しました。");
		}
		
		//レンタル希望画面を表示する処理
		CartForm cartForm = cartService.getCartByUserName(authentication.getName());		
		model.addAttribute("cartForm",cartForm);
		return "item/cart";

	}
	
	//レンタル希望を確認する
	@GetMapping(value = "/cart/confirm")
	public String cartComfirm(Model model, Authentication authentication)throws Exception{
		
		CartForm cartForm = cartService.getCartByUserName(authentication.getName());
		
		model.addAttribute("cartForm",cartForm);
		
		return "item/cartConfirm";
	}
	
	//レンタル希望を確定させて履歴に登録
	@GetMapping(value = "/cart/success")
	public String cartSuccess(Authentication authentication, Model model) throws Exception{
		int numOfRow = rentalService.registHistory(authentication.getName());
		
		if(numOfRow < 3) {
			model.addAttribute("message", "レンタル希望確定に失敗しました");
		}else {
			model.addAttribute("message", "レンタル希望を確定しました");
		}
		
		return "item/cartSuccess";
	}

	
}
