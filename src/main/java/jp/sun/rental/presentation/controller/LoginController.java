package jp.sun.rental.presentation.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jp.sun.rental.domain.entity.UserEntity;
import jp.sun.rental.presentation.form.UserForm;
import jp.sun.rental.session.UserSession;

@Controller
public class LoginController {
	

	//ログイン画面を表示する
	@GetMapping(value = "/login")
	public String login(@RequestParam Optional<String> error, Model model) {
		if(error.isPresent()) {
			model.addAttribute("error", "ユーザー名、またはパスワードが異なっているか、ログインの上限に達しています");
		}
		return "login";
	}
	
	
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
