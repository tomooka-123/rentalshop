package jp.sun.rental.application.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jp.sun.rental.domain.entity.UserEntity;
import jp.sun.rental.domain.repository.UserRepository;
import jp.sun.rental.presentation.form.UserForm;

@Service
public class UserSearchService {

	private UserRepository userRepository;
	private ModelMapper modelMapper;
	
	public UserSearchService(UserRepository userRepository, ModelMapper modelMapper) {
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}
	
	//フォームの情報を元にユーザーを検索する
	public List<UserForm> getUsersList(UserForm form) throws Exception {
		
		List<UserEntity> entityList = null;
		List<UserForm> formList = null;
		
		String userName = form.getUserName();
		
		//ユーザーネームが空なら全件検索、そうでないなら入力内容に応じてあいまい検索
		if(!userName.isEmpty()) {
			entityList = userRepository.getUsersByName(userName);
		}else {
			entityList = userRepository.getUsersAllList();
		}
		
		formList = convert(entityList);
		
		return formList;
	}
	
	//DBから取得したエンティティをhtml出力用のフォームに変換
	private List<UserForm> convert(List<UserEntity> entityList){
		
		List<UserForm> formList = new ArrayList<UserForm>();
		
		for (UserEntity entity : entityList) {
			UserForm form = modelMapper.map(entity, UserForm.class);
			formList.add(form);
		}
		
		return formList;
	}
}
