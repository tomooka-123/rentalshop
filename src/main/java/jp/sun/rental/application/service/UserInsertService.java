package jp.sun.rental.application.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jp.sun.rental.domain.UserRepository;
import jp.sun.rental.domain.entity.UserEntity;
import jp.sun.rental.presentation.form.UserInsertForm;

@Service
public class UserInsertService {
	
	//フィールド
	private UserRepository userRepository;
	private ModelMapper modelMapper;
				

	//コンストラクター
	public UserInsertService(UserRepository userRepository, ModelMapper modelMapper) {
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}
	
	//ユーザー情報をDBに登録するメソッド(これから書く)
	public int registUser(UserInsertForm form) {
		
		UserEntity entity = null;
		entity = convert(form);
		
		int resultRow = userRepository.regist(entity);
		
		return resultRow;
	}
	
	//FormをEntityに変換するメソッド
	private UserEntity convert(UserInsertForm form) {
		
		UserEntity entity = modelMapper.map(form, UserEntity.class);
		
		return entity;
	}

}
