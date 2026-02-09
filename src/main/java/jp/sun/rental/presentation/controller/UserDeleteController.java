package jp.sun.rental.presentation.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.sun.rental.application.service.UserDeleteService;
import jp.sun.rental.application.service.UserUpdateService;
import jp.sun.rental.presentation.form.UserForm;
import jp.sun.rental.presentation.form.UserUpdateForm;

@Controller
public class UserDeleteController {
	private UserDeleteService userDeleteService;
	private UserUpdateService userUpdateService;

	public UserDeleteController(UserDeleteService userDeleteService,UserUpdateService userUpdateService) {
		this.userDeleteService = userDeleteService;
		this.userUpdateService = userUpdateService;
	}
	
		// 削除確認画面
		@GetMapping("/deactivate")
		public String toDeactivateConfirm(Authentication authentication,@ModelAttribute("activate") UserForm form,Model model) {
			String userName = authentication.getName();
			UserUpdateForm useForm = userUpdateService.userUpdateToForm(userName);
			model.addAttribute("headline", "退会確認");
			model.addAttribute("activate", form);
			
			return "userdeleteregist";
		}

		// 退会処理実行
		@PostMapping("/deactivate")
		public String deactivate(Authentication authentication,				
				Model model,
				HttpServletRequest request,
		        HttpServletResponse response) {

			String userName = authentication.getName();

			try {
				// 論理削除(退会フラグをTrue
				userDeleteService.deactivateUser(userName);
			} catch (IllegalArgumentException ex) {
				model.addAttribute("errors", ex.getMessage());
				return "userdeleteconfirm";
			}
			
			// 強制ログアウト
			new SecurityContextLogoutHandler()
							.logout(request, response, authentication);
			// セッション破棄
//		    sessionStatus.setComplete();
		    model.addAttribute("message", "退会が完了しました");
		    return "user/success";
		    
		}
}