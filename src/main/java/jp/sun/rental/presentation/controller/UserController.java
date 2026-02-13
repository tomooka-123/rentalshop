package jp.sun.rental.presentation.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jp.sun.rental.application.service.UserInsertService;
import jp.sun.rental.application.service.UserSearchService;
import jp.sun.rental.application.service.UserUpdateService;
import jp.sun.rental.common.validator.groups.ValidGroupOrder;
import jp.sun.rental.presentation.form.ItemForm;
import jp.sun.rental.presentation.form.MemberForm;
import jp.sun.rental.presentation.form.UserForm;
import jp.sun.rental.presentation.form.UserInsertForm;
import jp.sun.rental.presentation.form.UserUpdateForm;

@Controller
@SessionAttributes( {"userInsertForm", "userUpdateForm"})
public class UserController {
	
	//フィールド
	private UserInsertService userInsertService;
	private UserSearchService userSearchService;
	private UserUpdateService userUpdateService;

	//コンストラクター
	public UserController(UserInsertService userInsertService, UserSearchService userSearchService, UserUpdateService userUpdateService) {
		this.userInsertService = userInsertService;
		this.userSearchService = userSearchService;
		this.userUpdateService = userUpdateService;
	}
	
	//TOP画面を表示する
	@GetMapping(value = "/top")
	public String toTop(Model model) {
		ItemForm itemForm = new ItemForm();
		
		model.addAttribute("itemForm", itemForm);
		
		return "top";
	}

	//ログイン画面を表示する
	@GetMapping(value = "/login")
	public String login(@RequestParam Optional<String> error, Model model) {
		if(error.isPresent()) {
			model.addAttribute("error", "ユーザー名、またはパスワードが異なっているか、ログインの上限に達しています");
		}
		return "login";
	}
	
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
		
		return "user/insertConfirm";
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
		
		return "user/search";
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
		
		return "user/search";
	}
	
	// ユーザー更新入力用
	@GetMapping(value = "/user/update")
	public String userUpdate( 
			Authentication authentication,
			Model model) {
		
		UserUpdateForm userUpdateForm = new UserUpdateForm();
		
		// ログイン中のユーザー名を取得
		String userName = authentication.getName();
		
		userUpdateForm = userUpdateService.userUpdateToForm(userName);
        // 画面に渡す
        model.addAttribute("userUpdateForm", userUpdateForm);
	    return "user/update";
	}
	
	// ユーザー情報更新確認用
	@PostMapping("/user/update")
	public String userUpdateConfirm(
	        Authentication authentication,
	        @Validated(ValidGroupOrder.class) @ModelAttribute("userUpdateForm") UserUpdateForm form,
	        BindingResult result,
	        Model model) {

	    // バリデーションエラー
	    if (result.hasErrors()) {
	        return "user/update";
	    }

	    // 確認画面に表示するだけ
	    model.addAttribute("userUpdateForm", form);
	    return "user/updateConfirm";
	}

	// ユーザー情報更新確認用
	@PostMapping(value = "/user/update/confirm")
	public String userUpdate(
		Authentication authentication,
		@SessionAttribute("userUpdateForm") UserUpdateForm form,
		SessionStatus sessionStatus,
		Model model) {
	
//		// ログイン中のユーザー名を取得
		String userName = authentication.getName();
	
		// UserUpdateEntityに値をセットし、更新する
		// 戻り値は更新数(int)
		userUpdateService.userUpdate(userName, form);
		
		model.addAttribute("message", "情報の更新が完了しました");
		
		// セッション破棄
		sessionStatus.setComplete();
		
	    return "user/success";

	}

	
}