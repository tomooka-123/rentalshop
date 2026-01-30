package jp.sun.rental.application.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.sun.rental.domain.entity.MemberEntity;
import jp.sun.rental.domain.entity.UserEntity;
import jp.sun.rental.domain.repository.UserRepository;
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
	
	//ユーザー情報をDBに登録するメソッド
	@Transactional(rollbackFor = Exception.class)
	public int registUser(UserInsertForm form) throws Exception{
		
		UserEntity userEntity = null;
		MemberEntity memberEntity = null;
		
		userEntity = convertUser(form);
		memberEntity = convertMember(form);
		
		int userResultRow = userRepository.registUser(userEntity);
		int memberResultRow = userRepository.registMember(memberEntity);
		
		int resultRow = userResultRow + memberResultRow;
		
		return resultRow;
	}
	
	//FormをUserEntityに変換するメソッド
	private UserEntity convertUser(UserInsertForm form) {
		
		UserEntity entity = modelMapper.map(form, UserEntity.class);
		
		return entity;
	}
	
	//FormをUserEntityに変換するメソッド
	private MemberEntity convertMember(UserInsertForm form) {
		
		MemberEntity entity = modelMapper.map(form, MemberEntity.class);
		
		return entity;
	}

}
