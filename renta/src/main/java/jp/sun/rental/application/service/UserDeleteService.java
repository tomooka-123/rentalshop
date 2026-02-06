package jp.sun.rental.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;
import jp.sun.rental.domain.repository.UserDeleteRepository;

@Service
public class UserDeleteService {
		
	private UserDeleteRepository userDeleteRepository;

	public UserDeleteService(UserDeleteRepository userDeleteRepository) {
		this.userDeleteRepository = userDeleteRepository;
	}

    /**
     * 指定ユーザーIDの退会処理
     * @param userId 退会対象のユーザーID
     * @param request HTTPリクエスト（セッション破棄用）
     */
	@Transactional
	public void deactivateUser(Long userId, HttpServletRequest request) {

		int result = userDeleteRepository.deleteByUserId(userId);

		if (result == 0) {
			throw new IllegalArgumentException("ユーザーが存在しない、または既に退会済みです");
		}

		request.getSession().invalidate();
	}
}
