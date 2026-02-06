package jp.sun.rental.application.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.sun.rental.domain.entity.UserEntity;
import jp.sun.rental.domain.entity.UserUpdateEntity;
import jp.sun.rental.domain.exception.UserNotFoundException;
import jp.sun.rental.domain.repository.UserRepository;
import jp.sun.rental.presentation.form.UserUpdateForm;

@Service
public class UserUpdateService {
	
	// フィールド、コンストラクタ=====================
	private UserRepository userRepository;
	private ModelMapper modelMapper;
	
	public UserUpdateService( UserRepository userRepository, ModelMapper modelMapper) {
	
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}
	
	public UserUpdateForm userUpdateToForm(String username) {
		
		//  引数チェック（呼び出し側のミス）
		if (username == null || username.isEmpty()) {
			throw new IllegalArgumentException("ユーザー名が指定されていません");
		}
		
		UserEntity user;
		try {
			// ユーザー名を元にUserEntityにデータをセット
			user = userRepository.getOnlyUserByName(username);

			//  業務エラー
			if (user == null) {
				throw new UserNotFoundException(username);
			}
		} catch (Exception e) {
			throw new UserNotFoundException("ユーザー取得中にエラーが発生しました: " + username);
		}
		
		// 取得したユーザー名を元にデータベースから情報を取得して、フォームにセット
		UserUpdateForm form = new UserUpdateForm();
		form.setUserName(username);
		form.setEmail(user.getEmail());
		form.setTell(user.getTell());
		form.setPassword(user.getPassword());
//		form.setPasswordRefel(user.getPassword());
		form.setAddress(user.getMembers().getAddress());
		form.setPost(user.getMembers().getPost());
		form.setName(user.getMembers().getName());
		form.setPlan(user.getMembers().getPlan());
		form.setCard(user.getMembers().getCard());
		
		return form;
    } 

	public UserUpdateEntity userUpdateToEntity( String username) {

	//  引数チェック（呼び出し側のミス）
			if (username == null || username.isEmpty()) {
				throw new IllegalArgumentException("ユーザー名が指定されていません");
			}
			
			UserEntity user;
			try {
				// ユーザー名を元にUserEntityにデータをセット
				user = userRepository.getOnlyUserByName(username);

				//  業務エラー
				if (user == null) {
					throw new UserNotFoundException(username);
				}
			} catch (Exception e) {
				throw new UserNotFoundException("ユーザー取得中にエラーが発生しました: " + username);
			}
		
		
		
	//	Entityに詰め替え(rowMapperに移動予定)
		UserUpdateEntity entity = new UserUpdateEntity();
		entity.setEmail(user.getEmail());
		entity.setTell(user.getTell());
//		entity.setPassword(user.getPassword());
		entity.setAddress(user.getMembers().getAddress());
		entity.setPost(user.getMembers().getPost());
		entity.setPlan(user.getMembers().getPlan());
		entity.setName(user.getMembers().getName());
		entity.setCard(user.getMembers().getCard());
		
		return entity;
    }
	// 修正
//	@Transactional( rollbackFor = Exception.class)
//	public int userUpd( String username, UserUpdateForm userUpdateForm) {
//		
//		int resurtRow = 0;
//		try {
//			// 値を取得し、ユーザー情報を更新する
//			resurtRow = userRepository.updateUser(userUpdateToEntity( username));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return resurtRow;
//	}
//	
	@Transactional(rollbackFor = Exception.class)
	public int userUpdate(String username, UserUpdateForm form) {

	    if (username == null || username.isEmpty()) {
	        throw new IllegalArgumentException("ユーザー名が指定されていません");
	    }

	    // Form → Entity（更新用）
	    UserUpdateEntity entity = new UserUpdateEntity();
	    entity.setUserName(username);
	    entity.setEmail(form.getEmail());
	    entity.setTell(form.getTell());
//	    entity.setPassword(form.getPassword());
	    entity.setAddress(form.getAddress());
	    entity.setPost(form.getPost());
	    entity.setPlan(form.getPlan());
	    entity.setName(form.getName());
	    entity.setCard(form.getCard());

	    return userRepository.updateUser(entity);
	}
}