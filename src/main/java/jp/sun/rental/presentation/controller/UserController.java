package jp.sun.rental.presentation.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.sun.rental.application.service.UserInsertService;
import jp.sun.rental.application.service.UserSearchService;
import jp.sun.rental.application.service.UserUpdateService;
import jp.sun.rental.domain.repository.UserRepository;
import jp.sun.rental.presentation.form.MemberForm;
import jp.sun.rental.presentation.form.UserForm;
import jp.sun.rental.presentation.form.UserInsertForm;
import jp.sun.rental.presentation.form.UserUpdateForm;


@Controller
@SessionAttributes("userForm")
public class UserController {
	
	//フィールド
	private UserInsertService userInsertService;
	private UserSearchService userSearchService;
	private UserUpdateService userUpdateService;
	private UserRepository userRepository;
	
	//コンストラクター
	public UserController(UserInsertService userInsertService,
						UserSearchService userSearchService,
						UserUpdateService userUpdateService) {
		
		this.userUpdateService = userUpdateService;
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
			model.addAttribute("error","登録に失敗しました。");
			return "error/error";
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
				model.addAttribute("userFormList", formList);
			}
		}
		
		return "userSearch";
	}

	@GetMapping(value = "/user/update")
	public String userUpdate( 
			Authentication authentication,
			Model model) {
		
		UserUpdateForm userUpdateForm = new UserUpdateForm();
		
		model.addAttribute("userUpdateForm",userUpdateForm);
		// ログイン中のユーザー名を取得
		String userName = authentication.getName();
		
		userUpdateForm = userUpdateService.userUpdateToForm(userName);
        // 画面に渡す
        model.addAttribute("userUpdateForm", userUpdateForm);
	    return "/user/userUpdate";
	}

	@PostMapping(value = "/user/update")
	public String userUpdate(
		Authentication authentication,
		@ModelAttribute UserUpdateForm form,
        BindingResult result,
        Model model) {
		
		
		// バリデーションエラー
		if (result.hasErrors()) {
			return "/user/update";
		}
	
		// ログイン中のユーザー名を取得
		String userName = authentication.getName();
	
		// UserUpdateEntityに値をセットし、更新する
		// 戻り値は更新数(int)
		userUpdateService.userUpdate(userName);
		
		
		// 確認画面へ 入力値をhtmlへ渡す
		model.addAttribute("user", form);
		return "user/update/confirm";



	}

}
//	public String updateForm(Model model, @ModelAttribute(value = "userForm")UserSession sessionUser) {
//		
//		// ユーザーidを受け取る
//		String userId = sessionUser.getUserId();
//		
//		UserForm userForm = new UserForm();
//		userForm.getUserName();		
//		userForm.getEmail();		
//		// ユーザーidを元に更新に必要な情報を抽出　entityにセット
//		/*
//		 * 
//		 * 		ユーザー名
//		//		メールアドレス
//		//		電話番号
//		//		郵便番号
//		//		住所
//		//		プラン名
//		 */
//		model.addAttribute("userEntity", new UserEntity());
//
//		model.addAttribute("headline", "ユーザー情報更新");
//		
//		model.addAttribute("userForm", new UserForm());
//		
//		// とりあえず＝＝＝＝＝＝＝＝＝
//		
//		model.addAttribute(userId); 
//		// ＝＝＝＝＝＝＝＝＝
//		return "userUpdate";
//	}
//	
//	@PostMapping(value = "/user/update")
//	public String update( @Validated @ModelAttribute UserForm userForm,
//			BindingResult result, Model model, @SessionAttribute(value = "loginUser", required = false) UserSession sessionUser) throws Exception {
//		
//		model.addAttribute("headline", "ユーザー情報更新");
//		
//		// 入力エラー
//		if (result.hasErrors()) {
//			return "userUpdate";
//		}
//		
////		// ★ userId はログインユーザーから取得
////		String userId = loginUserService.getLoginUserId();
//		
//		userForm.setUserId(sessionUser.getUserId());
//
//		// 更新処理
//		userUpdateService.update(userForm);
//
//		// 完了後
//		return "redirect:/user/update/complete";
//		
//	}
//	
//	@PutMapping("/me")
//	public ResponseEntity<Void> updateMe(
//	        @AuthenticationPrincipal LoginUser loginUser,
//	        @RequestBody UserUpdateEntity request) {
//
//	    UserUpdateService.update(loginUser.getUserId(), request);
//	    return ResponseEntity.ok().build();
//	}


