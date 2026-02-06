package jp.sun.rental.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jp.sun.rental.application.service.UserDeleteService;

@Controller
@RequestMapping("/deactivate")
public class UserDeleteController {
	private UserDeleteService userDeleteService;
	
	public UserDeleteController(UserDeleteService userDeleteService) {
		this.userDeleteService = userDeleteService;
	}
	
		// 削除確認画面
		@GetMapping("/deactivate")
		public String toDeactivateConfirm(Model model) {
			model.addAttribute("headline", "退会確認");
			return "deactivate";
		}

		// 退会処理実行
		@PostMapping("/deactivate")
		public String deactivate(HttpServletRequest request, Model model) {

			Long userId = (Long) request.getSession().getAttribute("userId");

			if (userId == null) {
				model.addAttribute("errors", "ログイン情報が無効です");
				return "/error/error";
			}

			try {
				userDeleteService.deactivateUser(userId, request);
			} catch (IllegalArgumentException ex) {
				model.addAttribute("errors", ex.getMessage());
				return "userDeactivateConfirm";
			}

			return "redirect:/user/deactivate/complete";
		}

		// 退会完了画面
		@GetMapping("/deactivate/complete")
		public String deactivateComplete(Model model) {
			model.addAttribute("headline", "退会完了");
			return "userDeactivateComplete";
		}
}