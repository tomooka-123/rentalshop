package jp.sun.rental.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jp.sun.rental.domain.entity.UserEntity;
import jp.sun.rental.presentation.form.UserForm;
import jp.sun.rental.session.UserSession;

@Controller
public class LoginController {
	
	
	
	@PostMapping("/login")
	public String login(
		@ModelAttribute UserForm form,
		HttpSession session) {

		// 認証成功したと仮定
		UserSession userSession = new UserSession();
		UserEntity userEntity = new UserEntity();
		
		userSession.setUserId(String.valueOf(userEntity.getUserId()));
		userSession.setUserName(userEntity.getUserName());
		userSession.setAuthority(userEntity.getAuthority());

		session.setAttribute("loginUser", userSession);

		return "redirect:/top";
	}

}
