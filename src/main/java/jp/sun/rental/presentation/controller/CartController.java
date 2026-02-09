package jp.sun.rental.presentation.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.sun.rental.application.service.CartService;
import jp.sun.rental.presentation.form.CartForm;

@Controller
public class CartController {

	private CartService cartService;
	
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}
	
	//ログイン中のユーザのカート情報を取得して中身表示用のページへ遷移
	@GetMapping(value = "/cart")
	public String toCart(Model model, Principal principal) throws Exception{
		
	CartForm cartForm = cartService.getCartByUserName(principal.getName());
		
	model.addAttribute("cartForm",cartForm);
	
	return "cart";
	}
}
