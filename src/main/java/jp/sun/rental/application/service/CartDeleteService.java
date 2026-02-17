package jp.sun.rental.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.sun.rental.domain.repository.CartRepository;
import jp.sun.rental.domain.repository.UserRepository;
import jp.sun.rental.presentation.form.CartItemForm;

@Service
public class CartDeleteService {
	
	private CartRepository cartRepository;
	private UserRepository userRepository;
	
	public CartDeleteService(CartRepository cartRepository, UserRepository userRepository) {
		this.cartRepository = cartRepository;
		this.userRepository = userRepository;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteItem(String userName,CartItemForm cartItemForm) throws Exception{
		
		//カートIDを取得
		int userId = userRepository.getUserIdByUserName(userName);
		int cartId = cartRepository.getCartId(userId);
		
		//アイテムIDを取得
		int itemId = Integer.parseInt(cartItemForm.getItemId());
		
		//DBから削除
		int numberOfRow = cartRepository.deleteItem(itemId, cartId);
		return numberOfRow;
	}
	
	//カートの中身を全削除
	@Transactional(rollbackFor = Exception.class)
	public int deleteAllItem(String username) throws Exception{
		int userId = userRepository.getUserIdByUserName(username);
		
		int numOfRow = cartRepository.deleteCartItemsByUserId(userId);
		
		return numOfRow;
	}
	
}
