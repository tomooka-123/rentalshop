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
	
	public List<UserForm> getUsersList(UserForm form) throws Exception {
		
		List<UserEntity> entityList = null;
		List<UserForm> formList = null;
		
		String userName = form.getUserName();
		
		if(!userName.isEmpty()) {
			entityList = userRepository.getUsersByName(userName);
		}else {
			entityList = userRepository.getUsersAllList();
		}
		
		formList = convert(entityList);
		
		return formList;
	}
	
	private List<UserForm> convert(List<UserEntity> entityList){
		
		List<UserForm> formList = new ArrayList<UserForm>();
		
		for (UserEntity entity : entityList) {
			UserForm form = modelMapper.map(entity, UserForm.class);
			formList.add(form);
		}
		
		return formList;
	}
}
