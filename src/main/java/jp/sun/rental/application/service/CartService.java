package jp.sun.rental.application.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jp.sun.rental.domain.entity.CartItemEntity;
import jp.sun.rental.domain.repository.CartRepository;
import jp.sun.rental.presentation.form.CartForm;
import jp.sun.rental.presentation.form.CartItemForm;

@Service
public class CartService {

	private CartRepository cartRepository;
	private ModelMapper modelMapper;
	
	public CartService(CartRepository cartRepository, ModelMapper modelMapper) {
		this.cartRepository = cartRepository;
		this.modelMapper = modelMapper;
	}
	
	public CartForm getCartByUserId(String userId) throws Exception{
		
		List<CartItemEntity> itemEntityList = null;
		List<CartItemForm> itemFormList = null;
		CartForm form = null;
		
		itemEntityList = cartRepository.getCartItemsListByUserId(userId);
		itemFormList = convert(itemEntityList);
		
		form.setCartId(itemFormList.);
		
	}
	
	private List<CartItemForm> convert(List<CartItemEntity> entityList){
		
		List<CartItemForm> formList = new ArrayList<CartItemForm>();
		
		for(CartItemEntity entity : entityList) {
			CartItemForm form = modelMapper.map(entity, CartItemForm.class);
			formList.add(form);
		}
		
		return formList;
	}
}
