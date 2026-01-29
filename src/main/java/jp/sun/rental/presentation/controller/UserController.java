package jp.sun.rental.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
	
	//フィールド
	
	//コンストラクターインジェクション

	
	/* 「～/top」にGet通信されたとき、TOP画面を返す */
	@GetMapping(value = "/top")
	public String toTop() {
		return "top";
	}

	/* 「～/login」にGet通信されたとき、ログイン画面を返す*/
	
	/* 「～/user/insert」にGet通信されたとき、ユーザー登録画面を返す*/
	
	/* 「～/user/insert」にPost通信されたとき、登録確認画面を返す
	 * 登録情報を受け取る（ModelからFormで）
	 * →Serviceに投げる
	 * →結果を受け取る（成功したという情報）
	 * →Viewに遷移する */
	
	/* 「～」にPost通信されたとき、ユーザー登録を実行し、登録完了画面を返す*/
	
}
