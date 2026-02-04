package jp.sun.rental.presentation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jp.sun.rental.application.service.UserInsertService;
import jp.sun.rental.application.service.UserSearchService;
import jp.sun.rental.common.validator.groups.ValidGroupOrder;
import jp.sun.rental.presentation.form.MemberForm;
import jp.sun.rental.presentation.form.UserForm;
import jp.sun.rental.presentation.form.UserInsertForm;

@Controller
@SessionAttributes("userInsertForm")
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
	
	//ユーザー登録用セッションオブジェクトの生成
	@ModelAttribute("userInsertForm")
	public UserInsertForm setupUserInsertForm() {
		return new UserInsertForm();
	}
	
	//会員プラン表示用
	private String setPlan(String plan) {
		switch (plan) {
		case "BASIC":
			return "お試しプラン";
		case "BRONZE":
			return "Bronzeプラン";
		case "SILVER":
			return "Silverプラン";
		case "GOLD":
			return "Goldプラン";
		default:
			return "回答なし";
		}
	}
	
	//クレジットカード番号表示用
	private String setCard(String card) {
		String asterisk = "*";
		String last4 = card.substring(card.length() - 4);
		for(int i = 1 ; i < card.length() - 4 ; i++) {
			asterisk += "*";
		}
		asterisk += last4;
		return asterisk;
	}
	
	//パスワード表示用
	private String setPassword(String password) {
		String asterisk = "*";
		for(int i = 1 ; i < password.length() ; i++) {
			asterisk += "*";
		}
		return asterisk;
	}
	
	//ユーザー登録入力画面を表示する
	@GetMapping(value = "/user/insert")
	public String toUserInsert(Model model, UserInsertForm userInsertForm) {
		
		//初期値登録
		userInsertForm.setPlan("BASIC");
		userInsertForm.setAuthority("GENERAL");//権限を一般ユーザーに設定
		userInsertForm.setUserPoint("0");
		
		//登録情報取得用Formオブジェクトを登録
		model.addAttribute("userInsertForm", userInsertForm);
		
		return "user/insert";
	}
	
	//ユーザー登録確認画面を表示する
	@PostMapping(value = "/user/insert")
	public String userInsertReview(@Validated(ValidGroupOrder.class) @ModelAttribute UserInsertForm userInsertForm, BindingResult result, Model model) throws Exception{
		
		if (result.hasErrors()) {
			return "user/insert";
		}
		
		//会員プラン表示用
		String plan = userInsertForm.getPlan();
		model.addAttribute("plan", setPlan(plan));
		
		//クレジットカード番号表示用
		String card = userInsertForm.getCard();
		model.addAttribute("card", setCard(card));
		
		//パスワード表示用
		String password = userInsertForm.getPassword();
		model.addAttribute("password", setPassword(password));
		
		model.addAttribute("userInsertForm", userInsertForm);
		
		return "user/review";
	}
	
	//ユーザー情報をDBに登録し、ユーザー登録完了画面を表示する
	@PostMapping(value = "/user/insert/submit")
	public String userInsert(@ModelAttribute UserInsertForm userInsertForm, Model model, SessionStatus status) throws Exception{
		
		int numberOfRow = userInsertService.registUser(userInsertForm);
		
		if (numberOfRow < 2) {
			model.addAttribute("error","登録に失敗しました。");
			return "error/error";
		}
		
		model.addAttribute("message", "ご登録ありがとうございます！");
		
		//セッション破棄
		status.setComplete();
		
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
				model.addAttribute("userFormList", formList);
			}
		}
		
		return "userSearch";
	}
	
	
	//例外ハンドラー
	@ExceptionHandler(Exception.class)
	public String handlerException(Exception e, Model model) {
		model.addAttribute("error", "システムエラーが発生しました");
		e.printStackTrace();
		
		return "error/error";
	}
	
}
