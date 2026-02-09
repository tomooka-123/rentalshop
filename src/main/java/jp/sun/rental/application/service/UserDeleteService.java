package jp.sun.rental.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.sun.rental.domain.entity.UserEntity;
import jp.sun.rental.domain.exception.UserNotFoundException;
import jp.sun.rental.domain.repository.UserDeleteRepository;
import jp.sun.rental.domain.repository.UserRepository;

@Service
public class UserDeleteService {
		
	private UserDeleteRepository userDeleteRepository;
	private UserRepository userRepository;

	public UserDeleteService(UserDeleteRepository userDeleteRepository,UserRepository userRepository) {
		this.userDeleteRepository = userDeleteRepository;
		this.userRepository = userRepository;
	}
	
    /**
     * 指定ユーザーIDの退会処理
     * @param userId 退会対象のユーザーID
     * @param request HTTPリクエスト（セッション破棄用）
     */
	@Transactional(rollbackFor = Exception.class)
	public void deactivateUser(String userName) {

		findUser(userName);
		// ユーザー存在チェック（未退会）
        UserEntity user = findUser(userName);
 
        // 退会フラグをTrueにする
        int result =
            userDeleteRepository.deleteByUserId(user.getUserId());

		if (result == 0) {
			throw new IllegalArgumentException("ユーザーが存在しない、または既に退会済みです");
		}

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

	/* public int deactivateUser(String username) {

        UserEntity user = findUser(username);
        int result = userDeleteRepository.deleteByUserId(user.getUserId());
        
        if (result == 0) {
            throw new RuntimeException("ユーザー削除に失敗しました");
        }

        return result;
        
		try {
			user = userRepository.getOnlyUserByName(username);
		} catch (Exception e) {
			throw new UserNotFoundException("ユーザー取得中にエラーが発生しました: " + username);
		}

        if (user == null) {
            throw new UserNotFoundException(username);
        }
        return user;
    } */