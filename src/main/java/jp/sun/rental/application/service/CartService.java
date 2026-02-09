package jp.sun.rental.application.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jp.sun.rental.domain.entity.CartEntity;
import jp.sun.rental.domain.entity.CartItemEntity;
import jp.sun.rental.domain.repository.CartRepository;
import jp.sun.rental.domain.repository.UserRepository;
import jp.sun.rental.presentation.form.CartForm;

@Service
public class CartService {

	private CartRepository cartRepository;
	private UserRepository userRepository;
	private ModelMapper modelMapper;
	
	public CartService(CartRepository cartRepository, UserRepository userRepository, ModelMapper modelMapper) {
		this.cartRepository = cartRepository;
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}
	
	//ユーザ名からそのユーザが所持しているカートの情報を取得
	public CartForm getCartByUserName(String username) throws Exception{
		
		List<CartItemEntity> itemEntityList = null;
		CartEntity cartEntity = new CartEntity();
		CartForm cartForm = null;
		
		int userId = userRepository.getUserIdByUserName(username);
		
		cartEntity = cartRepository.getCartItemsListByUserId(userId);
		
		cartEntity.setUserId(userId);
		cartEntity.setCartItems(itemEntityList);
		
		convert(cartEntity);
		
		return cartForm;
		
	}
	
	//エンティティをフォームに変換
	private CartForm convert(CartEntity entity){
		
		CartForm form = new CartForm();
		
		form = modelMapper.map(entity, CartForm.class);
		
		return form;
	}
}
