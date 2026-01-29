package jp.sun.rental.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jp.sun.rental.application.service.UserInsertService;
import jp.sun.rental.presentation.form.UserInsertForm;

@Controller
public class UserController {
	
	//フィールド
	private UserInsertService userInsertService;
	
	//コンストラクター
	public UserController(UserInsertService userInsertService) {
		this.userInsertService = userInsertService;
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
	public String userInsert(@ModelAttribute UserInsertForm userInsertForm, Model model) {
		
		int numberOfRow = userInsertService.registUser(userInsertForm);
		
		if (numberOfRow == 0) {
			model.addAttribute("message","登録に失敗しました。");
			return "error";
		}
		
		model.addAttribute("message", "ご登録ありがとうございます！");
		
		return "user/success";
	}
	
	
	
}
