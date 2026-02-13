package jp.sun.rental.application.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.sun.rental.domain.entity.CartEntity;
import jp.sun.rental.domain.entity.CartItemEntity;
import jp.sun.rental.domain.entity.MemberEntity;
import jp.sun.rental.domain.entity.RentalHistoryEntity;
import jp.sun.rental.domain.entity.RentalItemEntity;
import jp.sun.rental.domain.entity.UserEntity;
import jp.sun.rental.domain.repository.CartRepository;
import jp.sun.rental.domain.repository.RentalRepository;
import jp.sun.rental.domain.repository.UserRepository;
import jp.sun.rental.presentation.form.ItemForm;
import jp.sun.rental.presentation.form.RentalHistoryForm;
import jp.sun.rental.presentation.form.RentalItemForm;

@Service
public class RentalService {

	RentalRepository rentalRepository;
	UserRepository userRepository;
	CartRepository cartRepository;
	ModelMapper modelMapper;
	
	public RentalService(RentalRepository rentalRepository, UserRepository userRepository,
			CartRepository cartRepository, ModelMapper modelMapper) {
		this.rentalRepository = rentalRepository;
		this.userRepository = userRepository;
		this.cartRepository = cartRepository;
		this.modelMapper = modelMapper;
	}
	
	//ユーザー名からそのユーザーの履歴を取得
	public List<RentalHistoryForm> getHistoryListByUserName(String username) throws Exception{
		
		List<RentalHistoryEntity> historyEntityList = new ArrayList<RentalHistoryEntity>();
		List<RentalHistoryForm> historyFormList = new ArrayList<RentalHistoryForm>();
		
		int userId = userRepository.getUserIdByUserName(username);
		
		historyEntityList = rentalRepository.getRentalHistoryListByUserId(userId);
		
		for (RentalHistoryEntity historyEntity : historyEntityList) {
	        RentalHistoryForm historyForm = convert(historyEntity);
	        historyFormList.add(historyForm);
	    }
		
		return historyFormList;
	}
	
	//カート情報から履歴に追加、追加後カートの破棄
	@Transactional(rollbackFor = Exception.class)
	public int registHistory(String username)throws Exception{
		int userId = userRepository.getUserIdByUserName(username);
		
		CartEntity cart = cartRepository.getCartItemsListByUserId(userId);
		
		if(cart == null || cart.getCartItems().isEmpty()) {
			throw new Exception("カートが空です");
		}
		
		int rowRental = 0;
		int rentalId = 0;
		UserEntity user = userRepository.getOnlyUserByName(username);
		MemberEntity member = user.getMembers();
		
		//レンタル希望のユーザーID、日時などを登録し最後に追加された主キーを取得
		rowRental = rentalRepository.registRental(userId, member);
		rentalId = rentalRepository.getLastInsertId();
		
		//カートの中身を履歴に登録
		int rowItems = 0;
		for(CartItemEntity items : cart.getCartItems()) {
			rowItems += rentalRepository.registRentalItems(rentalId, items);
		}
		
		//カートの中身を破棄
		int cartDelete = 0;
		
		cartDelete = cartRepository.deleteCartItemsByUserId(userId);
		
		return rowRental + rowItems + cartDelete;
	}
	
	//返却フラグ切り替え
	@Transactional(rollbackFor = Exception.class)
	public int changeReturnFlag(String rentalItemId)throws Exception{
		int itemId = Integer.parseInt(rentalItemId);
		
		int numRow = 0;
		
		numRow = rentalRepository.changeReturnFlag(itemId);
		
		return numRow;
	}
	
	//論理削除フラグ切り替え
	@Transactional(rollbackFor = Exception.class)
	public int changeDeleteFlag(String rentalItemId)throws Exception{
		int itemId = Integer.parseInt(rentalItemId);
		
		int numRow = 0;
		
		numRow = rentalRepository.changeDeleteFlag(itemId);
		
		return numRow;
	}
	
	private RentalHistoryForm convert(RentalHistoryEntity historyEntity) {
		
		RentalHistoryForm historyForm = new RentalHistoryForm();
		List<RentalItemForm> rentalItemForms = new ArrayList<RentalItemForm>();
		
		historyForm = modelMapper.map(historyEntity, RentalHistoryForm.class);
		
		for (RentalItemEntity rentalItemEntity : historyEntity.getRentalItems()) {
			RentalItemForm rentalItemForm = modelMapper.map(rentalItemEntity, RentalItemForm.class);
			
			rentalItemForm.setItemForm(modelMapper.map(rentalItemEntity.getItemEntity(), ItemForm.class));
			rentalItemForms.add(rentalItemForm);
		}
		historyForm.setRentalItems(rentalItemForms);
		
		return historyForm;
	}
}
