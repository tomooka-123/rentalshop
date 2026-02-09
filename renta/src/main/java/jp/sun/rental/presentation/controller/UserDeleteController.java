package jp.sun.rental.presentation.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;

import jp.sun.rental.application.service.UserDeleteService;
import jp.sun.rental.application.service.UserUpdateService;
import jp.sun.rental.common.validator.groups.ValidGroupOrder;
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
		public String deactivate(Authentication authentication,@Validated(ValidGroupOrder.class) @ModelAttribute("activate") UserForm form,BindingResult result,SessionStatus sessionStatus,Model model) {

			String userName = authentication.getName();
			userDeleteService.deactivateUser(userName);
			
			// (Long) request.getSession().getAttribute("userId");

			// セッション破棄
		    sessionStatus.setComplete();

		    model.addAttribute("message", "退会が完了しました");

			try {
				userDeleteService.deactivateUser(userName);
			} catch (IllegalArgumentException ex) {
				model.addAttribute("errors", ex.getMessage());
				return "userdeleteconfirm";
			}

		    return "user/success";
			// return "userdeleteconfirm";
		}

		// 退会完了画面
		@GetMapping("/deactivate/complete")
		public String deactivateComplete(Model model) {
			model.addAttribute("headline", "退会完了");
			return "userdeleteconfirm";
		}
}