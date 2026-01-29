package jp.sun.rental.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import jp.sun.rental.presentation.form.UserInsertForm;

@Controller
public class UserController {
	
	//フィールド
	
	//コンストラクターインジェクション
	public UserController() {
		
	}
	
	//セッションセッションオブジェクトの生成
	@ModelAttribute("userInsertForm")
	public UserInsertForm setupUserInsertForm() {
		return new UserInsertForm();
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
	public String toUserInsert(Model model, UserInsertForm userInsertForm) {
		
		//登録情報取得用Formオブジェクトを登録
		model.addAttribute("userInsertForm", userInsertForm);
		
		return "user/insert";
	}
	
	/* 「～/user/insert」にPost通信されたとき、登録確認画面を返す
	 * 登録情報を受け取る（ModelからFormで）
	 * →Serviceに投げる
	 * →結果を受け取る（成功したという情報）
	 * →Viewに遷移する(user/confirm) */
	
	/* 「～」にPost通信されたとき、ユーザー登録を実行し、登録完了画面(user/success)を返す*/
	
}
