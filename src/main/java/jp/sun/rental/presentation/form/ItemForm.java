package jp.sun.rental.presentation.form;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jp.sun.rental.common.validator.groups.ValidGroup1;
import jp.sun.rental.common.validator.groups.ValidGroup2;
import lombok.Data;

@Data
public class ItemForm implements Serializable {

	private String itemId;
	
	@NotEmpty(message = "商品名は必須です", groups = ValidGroup1.class)
	@Pattern(regexp = "^.{1,50}$", message = "ユーザー名は1〜50文字の半角英数字のみ使用できます", groups = ValidGroup2.class)
	private String itemName;
	
	@NotEmpty(message = "カテゴリ番号は必須です", groups = ValidGroup1.class)
	@Pattern(regexp = "^[0-9]{1,2}$", message = "カテゴリ番号は２桁以下、数値で入力してください", groups = ValidGroup2.class)
	private String genreId;
	
	@NotEmpty(message = "商品画像は必須です", groups = ValidGroup1.class)
	private String itemImg;
	
	private String itemUpdate;
	
	private String artist;
	
	private String director;
	
	@NotEmpty(message = "ポイントは必須です", groups = ValidGroup1.class)
	@Pattern(
	  regexp = "^[0-9]{1,5}$",
	  message = "ポイントは5桁以下の数値で入力してください",
	  groups = ValidGroup2.class
	)
	private String itemPoint;
}
