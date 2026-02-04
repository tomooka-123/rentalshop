package jp.sun.rental.presentation.form;

import java.io.Serializable;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jp.sun.rental.common.annotation.UniqueEmail;
import jp.sun.rental.common.annotation.UniqueUserName;
import jp.sun.rental.common.validator.groups.ValidGroup1;
import jp.sun.rental.common.validator.groups.ValidGroup2;
import jp.sun.rental.common.validator.groups.ValidGroup3;
import lombok.Data;

@Data
public class UserInsertForm implements Serializable {
	
	private String userId;
	
	@NotEmpty(message = "ユーザー名は必須入力です", groups = ValidGroup1.class)
	@Pattern(regexp = "^[a-zA-Z0-9_.-]{5,20}$", message = "ユーザー名は5〜20文字の半角英数字のみ使用できます", groups = ValidGroup2.class)
	@UniqueUserName(message = "このユーザー名は既に登録されています", groups = ValidGroup3.class)
	private String userName;
	
	@NotEmpty(message = "氏名は必須入力です", groups = ValidGroup1.class)
	@Size(max=100, message = "氏名は100文字以下で入力してください", groups = ValidGroup2.class)
	private String name;
	
	@NotEmpty(message = "メールアドレスは必須入力です", groups = ValidGroup1.class)
	@Email(message = "メールアドレス形式で入力してください", groups = ValidGroup2.class)
	@UniqueEmail(message = "このメールアドレスは既に登録されています", groups = ValidGroup3.class)
	private String email;
	
	@NotEmpty(message = "電話番号は必須入力です", groups = ValidGroup1.class)
	@Pattern(regexp = "^[0-9]{10,11}$", message = "電話番号はハイフンなし、10桁以上11桁以下、半角数字で入力してください", groups = ValidGroup2.class)
	private String tell;
	
	@NotEmpty(message = "郵便番号は必須入力です", groups = ValidGroup1.class)
	@Pattern(regexp = "^[0-9]{7}$", message = "郵便番号はハイフンなし、7桁、半角数字で入力してください", groups = ValidGroup2.class)
	private String post;
	
	@NotEmpty(message = "住所は必須入力です", groups = ValidGroup1.class)
	@Size(max=80, message = "住所は80文字以下で入力してください", groups = ValidGroup2.class)
	private String address;
	
	@NotEmpty(message = "会員プランを選択してください", groups = ValidGroup1.class)
	private String plan;
	
	@NotEmpty(message = "クレジットカード番号は必須入力です", groups = ValidGroup1.class)
	@Pattern(regexp = "^[0-9]{2,16}$", message = "クレジットカード番号はハイフンなし、16桁、半角数字で入力してください", groups = ValidGroup2.class)
	private String card;	
	
	@NotEmpty(message = "パスワードは必須入力です", groups = ValidGroup1.class)
	@Size(min = 5, message = "パスワードは5文字以上入力してください", groups = ValidGroup2.class)
	@Size(max = 72, message = "パスワードは72文字以下で入力してください", groups = ValidGroup3.class)
	private String password;
	
	@NotEmpty(message = "確認用パスワードは必須入力です", groups = ValidGroup1.class)
	private String confirmPassword;
	
	private String authority;
	
	private String userPoint;
	
	
	
	@AssertTrue(message = "パスワードと確認用パスワードは同一にしてください", groups = ValidGroup3.class)
	public boolean isPasswordValid() {
		if (password == null || password.isEmpty()) {
			return true;
		}
		return password.equals(confirmPassword);
	}


}
