package jp.sun.rental.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jp.sun.rental.presentation.form.UserInsertForm;

@Controller
public class UserController {
	
	//フィールド
	
	//コンストラクターインジェクション
	public UserController() {
		
	}
	
	/* 「～/top」にGet通信されたとき、TOP画面を返す */
	@GetMapping(value = "/top")
	public String toTop() {
		return "top";
	}

	/* 「～/login」にGet通信されたとき、ログイン画面を返す*/
	/* @GetMapping(value = "/login")
	public String login() {
		return "login";
	} */
	
	/* 「～/user/insert」にGet通信されたとき、ユーザー登録画面を返す*/
	@GetMapping(value = "/user/insert")
	public String toUserInsert(Model model) {
		
		//登録情報取得用Formオブジェクトを登録
		model.addAttribute("userInsertForm", new UserInsertForm());
		
		return "user/insert";
	}
	
	/* 「～/user/insert」にPost通信されたとき、登録確認画面(user/review)を返す */
	@PostMapping(value = "/user/insert")
	public String userInsertReview(@Validated @ModelAttribute UserInsertForm userInsertForm, BindingResult result, Model model) throws Exception{
		
		if (result.hasErrors()) {
			return "error";
		}
		
		model.addAttribute("userInsertForm", userInsertForm);
		
		return "user/review";
	}
	
	/* 「～」にPost通信されたとき、ユーザー登録を実行し、登録完了画面(user/success)を返す
	 * 登録情報を受け取る
	 * →Serviceに投げる
	 * →結果を受け取る（成功したという情報）
	 * →Viewに遷移する */
	
}
