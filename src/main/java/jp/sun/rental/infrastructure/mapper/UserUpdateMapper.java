package jp.sun.rental.infrastructure.mapper;

import org.springframework.stereotype.Component;

import jp.sun.rental.domain.entity.UserEntity;
import jp.sun.rental.domain.entity.UserUpdateEntity;
import jp.sun.rental.presentation.form.UserUpdateForm;

@Component
public class UserUpdateMapper {
	// アプリMapper
	// 画面表示用
	 public UserUpdateForm toForm(String username, UserEntity user) {
		 
		UserUpdateForm form = new UserUpdateForm();
		
		form.setUserName(username);
		form.setEmail(user.getEmail());
		form.setTell(user.getTell());
		form.setAddress(user.getMembers().getAddress());
		form.setPost(user.getMembers().getPost());
	    form.setName(user.getMembers().getName());
	    form.setPlan(user.getMembers().getPlan());
	    form.setCard(user.getMembers().getCard());
	    
	    return form;
	  }
	 
	 // 確定更新用（Form → Entity）
	 public UserUpdateEntity toEntity(String username, UserUpdateForm form) {

        UserUpdateEntity entity = new UserUpdateEntity();
        
        entity.setUserName(username);
        entity.setEmail(form.getEmail());
        entity.setTell(form.getTell());
        entity.setAddress(form.getAddress());
        entity.setPost(form.getPost());
        entity.setPlan(form.getPlan());
        entity.setName(form.getName());
        entity.setCard(form.getCard());
//	    entity.setPassword(form.getPassword());

        return entity;
	 }
	 
	//初期表示用（Entity → 更新Entity）
	 public UserUpdateEntity fromUserEntity(UserEntity user) {
		 
		UserUpdateEntity entity = new UserUpdateEntity();
		entity.setEmail(user.getEmail());
		entity.setTell(user.getTell());
		entity.setAddress(user.getMembers().getAddress());
		entity.setPost(user.getMembers().getPost());
		entity.setPlan(user.getMembers().getPlan());
		entity.setName(user.getMembers().getName());
		entity.setCard(user.getMembers().getCard());
		
		return entity;
	 }
}