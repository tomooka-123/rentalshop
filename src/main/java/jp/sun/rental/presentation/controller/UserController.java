package jp.sun.rental.presentation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jp.sun.rental.application.service.UserInsertService;
import jp.sun.rental.application.service.UserSearchService;
import jp.sun.rental.presentation.form.MemberForm;
import jp.sun.rental.presentation.form.UserForm;
import jp.sun.rental.presentation.form.UserInsertForm;

@Controller
public class UserController {
	
	//フィールド
	private UserInsertService userInsertService;
	private UserSearchService userSearchService;

	//コンストラクター
	public UserController(UserInsertService userInsertService, UserSearchService userSearchService) {
		this.userInsertService = userInsertService;
		this.userSearchService = userSearchService;
	}
	
	//TOP画面を表示する
	@GetMapping(value = "/top")
	public String toTop() {
		return "top";
	}

	//ログイン画面を表示する
	/* @GetMapping(value = "/login")
	public String login() {
		return "login";
	} */
	
	
	
	//ユーザー登録入力画面を表示する
	@GetMapping(value = "/user/insert")
	public String toUserInsert(Model model) {
		
		//登録情報取得用Formオブジェクトを登録
		model.addAttribute("userInsertForm", new UserInsertForm());
		
		return "user/insert";
	}
	
	//ユーザー登録確認画面を表示する
	@PostMapping(value = "/user/insert")
	public String userInsertReview(@Validated @ModelAttribute UserInsertForm userInsertForm, BindingResult result, Model model) throws Exception{
		
		if (result.hasErrors()) {
			return "user/insert";
		}
		
		model.addAttribute("userInsertForm", userInsertForm);
		
		return "user/review";
	}
	
	//ユーザー情報をDBに登録し、ユーザー登録完了画面を表示する
	@PostMapping(value = "/user/insert/submit")
	public String userInsert(@ModelAttribute UserInsertForm userInsertForm, Model model) throws Exception{
		
		int numberOfRow = userInsertService.registUser(userInsertForm);
		
		if (numberOfRow < 2) {
			model.addAttribute("message","登録に失敗しました。");
			return "error";
		}
		
		model.addAttribute("message", "ご登録ありがとうございます！");
		
		return "user/success";
	}
	
	
	
	
	//管理者用ユーザー検索メソッド
	@GetMapping(value = "/search/user")
	public String toUserSearch(Model model) {
		
		UserForm userForm = new UserForm();
		MemberForm memberForm = new MemberForm();
		
		model.addAttribute("userForm", userForm);
		model.addAttribute("memberForm", memberForm);
		
		return "userSearch";
	}
	
	@PostMapping(value = "/search/user")
	public String searchUsers(@ModelAttribute UserForm userForm, BindingResult result, Model model) throws Exception {
		if (result.hasErrors()) {
			return "userSearch";
		}else {
			List<UserForm> formList = userSearchService.getUsersList(userForm);
			if (formList != null && !formList.isEmpty()) {
				model.addAttribute("UserForm", formList);
			}
		}
		
		return "userSearch";
	}
	
	
	
	
}
