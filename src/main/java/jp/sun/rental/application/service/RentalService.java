package jp.sun.rental.application.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jp.sun.rental.domain.entity.RentalHistoryEntity;
import jp.sun.rental.domain.entity.RentalItemEntity;
import jp.sun.rental.domain.repository.RentalRepository;
import jp.sun.rental.domain.repository.UserRepository;
import jp.sun.rental.presentation.form.ItemForm;
import jp.sun.rental.presentation.form.RentalHistoryForm;
import jp.sun.rental.presentation.form.RentalItemForm;

@Service
public class RentalService {

	RentalRepository rentalRepository;
	UserRepository userRepository;
	ModelMapper modelMapper;
	
	public RentalService(RentalRepository rentalRepository, UserRepository userRepository, ModelMapper modelMapper) {
		this.rentalRepository = rentalRepository;
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}
	
	//ユーザー名からそのユーザーの履歴を取得
	public RentalHistoryForm getHistoryByUserName(String username) throws Exception{
		
		RentalHistoryEntity historyEntity = new RentalHistoryEntity();
		RentalHistoryForm historyForm = null;
		
		int userId = userRepository.getUserIdByUserName(username);
		
		historyEntity = rentalRepository.getRentalHistoryByUserId(userId);
		
		historyForm = convert(historyEntity);
		
		return historyForm;
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
