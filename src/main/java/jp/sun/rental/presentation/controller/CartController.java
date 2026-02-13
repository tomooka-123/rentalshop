package jp.sun.rental.presentation.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jp.sun.rental.application.service.CartInsertService;
import jp.sun.rental.application.service.CartService;
import jp.sun.rental.presentation.form.CartForm;
import jp.sun.rental.presentation.form.CartItemForm;

@Controller
public class CartController {

	private CartService cartService;
	private CartInsertService cartInsertService;
	
	public CartController(CartService cartService, CartInsertService cartInsertService) {
		this.cartService = cartService;
		this.cartInsertService = cartInsertService;
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
		    model.addAttribute("message", "既に登録されています");
			
		} catch (Exception e) {
			model.addAttribute("error","レンタル希望の登録に失敗しました。");
			e.printStackTrace();
			return "error/error";
		}
		
		CartForm cartForm = cartService.getCartByUserName(authentication.getName());		
		model.addAttribute("cartForm",cartForm);

		return "item/cart";
	}
	
}
