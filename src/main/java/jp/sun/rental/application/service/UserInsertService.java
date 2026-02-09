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
	//private BCryptPasswordEncoder passwordEncoder;

	//コンストラクター
	public UserInsertService(UserRepository userRepository, ModelMapper modelMapper/*, BCryptPasswordEncoder passwordEncoder*/) {
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
		//this.passwordEncoder = passwordEncoder;
	}
	
	//ユーザー情報をDBに登録するメソッド
	@Transactional(rollbackFor = Exception.class)
	public int registUser(UserInsertForm form) throws Exception{
		
		UserEntity userEntity = null;
		MemberEntity memberEntity = null;
		
		//FormをEntityに変換
		userEntity = convertUser(form);
		memberEntity = convertMember(form);
		
		//パスワードをハッシュ化
		//userEntity.setPassword(passwordEncoder.encode(form.getPassword()));
		
		//authorityが未設定の場合、一般ユーザーに設定する
		if(userEntity.getAuthority() == null) {
			userEntity.setAuthority("GENERAL");
		}
		
		//登録
		int resultRow = userRepository.regist(userEntity, memberEntity);
		
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