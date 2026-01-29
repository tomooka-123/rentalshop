package jp.sun.rental.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.sun.rental.application.service.UserSearchService;
import jp.sun.rental.presentation.form.MemberForm;
import jp.sun.rental.presentation.form.UserForm;

@Controller
public class UserController {

	private UserSearchService userSearchService;
	
	public UserController(UserSearchService userSearchService) {
		this.userSearchService = userSearchService;
	}
	
	@GetMapping(value = "/search/user")
	public String toUserSearch(Model model) {
		
		UserForm userForm = new UserForm();
		MemberForm memberForm = new MemberForm();
		
		model.addAttribute("userForm", userForm);
		model.addAttribute("memberForm", memberForm);
		
		return "userSearch";
	}
	
}
