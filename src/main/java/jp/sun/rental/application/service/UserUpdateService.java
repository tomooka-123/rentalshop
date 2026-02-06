package jp.sun.rental.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.sun.rental.domain.entity.UserEntity;
import jp.sun.rental.domain.entity.UserUpdateEntity;
import jp.sun.rental.domain.exception.UserNotFoundException;
import jp.sun.rental.domain.repository.UserRepository;
import jp.sun.rental.infrastructure.mapper.UserUpdateMapper;
import jp.sun.rental.presentation.form.UserUpdateForm;

@Service
public class UserUpdateService {
	
	// フィールド、コンストラクタ=====================
	private UserRepository userRepository;
	private UserUpdateMapper userUpdateMapper;
	
	public UserUpdateService( UserRepository userRepository, UserUpdateMapper userUpdateMapper) {
	
		this.userRepository = userRepository;
		this.userUpdateMapper = userUpdateMapper;
	}
	
	public UserUpdateForm userUpdateToForm(String username) {
		
		UserEntity user = findUser(username);
		
		return userUpdateMapper.toForm(username, user);
    } 

	
		

	@Transactional(rollbackFor = Exception.class)
	public int userUpdate(String username, UserUpdateForm form) {

	    if (username == null || username.isEmpty()) {
	        throw new IllegalArgumentException("ユーザー名が指定されていません");
	    }

	    // Form → Entity（更新用）
	    UserUpdateEntity entity = userUpdateMapper.toEntity(username, form);

	    return userRepository.updateUser(entity);

	}
	
	private UserEntity findUser(String username) {

        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("ユーザー名が指定されていません");
        }

        UserEntity user = null;
		try {
			user = userRepository.getOnlyUserByName(username);
		} catch (Exception e) {
			throw new UserNotFoundException("ユーザー取得中にエラーが発生しました: " + username);
		}

        if (user == null) {
            throw new UserNotFoundException(username);
        }
        return user;
    }
}