package jp.sun.rental.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.sun.rental.domain.repository.CartRepository;
import jp.sun.rental.domain.repository.UserRepository;
import jp.sun.rental.presentation.form.CartItemForm;

@Service
public class CartInsertService {
	
	private CartRepository cartRepository;
	private UserRepository userRepository;
	
	public CartInsertService(CartRepository cartRepository, UserRepository userRepository) {
		this.cartRepository = cartRepository;
		this.userRepository = userRepository;
	}
	
	//商品をレンタル希望に追加する
	@Transactional(rollbackFor = Exception.class)
	public int addCart(String userName,CartItemForm cartItemForm) throws Exception{
		
		int userId = userRepository.getUserIdByUserName(userName);
		int cartId = cartRepository.getCartId(userId);
		int itemId = Integer.parseInt(cartItemForm.getItemId());
		
		int numberOfRow = cartRepository.addCart(itemId, cartId);
		
		return numberOfRow;
	}

}
