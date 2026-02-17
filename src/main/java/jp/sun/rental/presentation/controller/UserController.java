package jp.sun.rental.presentation.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
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

import jp.sun.rental.application.service.ProductService;
import jp.sun.rental.application.service.UserInsertService;
import jp.sun.rental.application.service.UserSearchService;
import jp.sun.rental.application.service.UserUpdateService;
import jp.sun.rental.common.validator.groups.ValidGroupOrder;
import jp.sun.rental.domain.entity.GenreEntity;
import jp.sun.rental.domain.entity.ItemEntity;
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
	private ProductService productService;

	//コンストラクター
	public UserController(UserInsertService userInsertService, UserSearchService userSearchService, UserUpdateService userUpdateService, ProductService productService) {
		this.userInsertService = userInsertService;
		this.userSearchService = userSearchService;
		this.userUpdateService = userUpdateService;
		this.productService = productService;
	}
	
	//TOP画面を表示する
	@GetMapping(value = "/top")
	public String toTop(@RequestParam(required = false) Integer genreId
						,Model model) {
		
		Integer selectedId = (genreId != null) ? genreId : 1;
		
		List<GenreEntity> categoryList = productService.getCategoryList();

	    String selectedGenreName = categoryList.stream()
	            .filter(c -> c.getGenreId().equals(selectedId))
	            .findFirst()
	            .map(GenreEntity::getGenreName)
	            .orElse("");
	    
		ItemForm itemForm = new ItemForm();
		
		List<ItemEntity> products = productService.getProductList();
		model.addAttribute("itemForm", itemForm);
		model.addAttribute("products", products);
		model.addAttribute("newTop5", productService.getNewTop5());
		model.addAttribute("oldTop5", productService.getOldTop5());
		
		model.addAttribute("categoryList", productService.getCategoryList());
		model.addAttribute("selectedGenreId", selectedId);
	    model.addAttribute("selectedGenreName", selectedGenreName);
	    model.addAttribute("musicList",
	            productService.getRandomByGenre(selectedId));
	    
		return "top";
	}
	
	// 管理者ログイン時の商品登録
	@GetMapping("/admin/product/new")
	@PreAuthorize("hasAuthority('EMPLOYEE')")
	public String showProductForm(@ModelAttribute("itemForm") ItemForm itemForm, Model model) {
		
		//初期値登録
		itemForm.setItemPoint("25");
		itemForm.setItemImg("/images/");
		
	    model.addAttribute("genreList", productService.getCategoryList());
	    
	    return "admin/product-form";
	}
	
	@PostMapping("/admin/product/save")
	@PreAuthorize("hasAuthority('EMPLOYEE')")
	public String saveProduct(@ModelAttribute("genreList") GenreEntity genreEntity,
							@Validated(ValidGroupOrder.class) @ModelAttribute ItemForm form,  
							BindingResult result,
							Model model) {

		if (result.hasErrors()) {
			
			model.addAttribute("genreList", productService.getCategoryList());
			
			return "admin/product-form";
		}
		
	    productService.save(form);
	    return "redirect:/top";
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
		
		//会員プラン表示用
		String plan = form.getPlan();
		model.addAttribute("plan", setPlan(plan));
		
		//クレジットカード番号表示用
		String card = form.getCard();
		model.addAttribute("card", setCard(card));

	    // 確認画面に表示するだけ
	    model.addAttribute("userUpdateForm", form);
	    return "user/updateConfirm";
	}

	// ユーザー情報更新実行用
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
	
	//権限不足でページに飛ぼうとした場合にエラーページへ飛ばす
	@GetMapping(value = "/error/403")
	public String accessDeined(Model model) {
		model.addAttribute("error", "権限が不足しているため接続が拒否されました");

		return "error/error";
	}

	
}