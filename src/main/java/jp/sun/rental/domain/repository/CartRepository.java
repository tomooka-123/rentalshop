package jp.sun.rental.domain.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import jp.sun.rental.domain.entity.CartEntity;
import jp.sun.rental.infrastructure.mapper.CartResultSetExtractor;

@Repository
public class CartRepository {

	private ResultSetExtractor<CartEntity> cartExtractor = new CartResultSetExtractor();
	private JdbcTemplate jdbcTemplate;
	
	public CartRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	//ユーザーIDからカートの中身を検索
	public CartEntity getCartItemsListByUserId(int userId) throws Exception{
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT");
		sb.append(" c.cart_id, c.user_id,");
		sb.append(" ci.cart_item_id, ci.item_id, ci.cart_id,");
		sb.append(" i.item_id, i.item_name, i.genre_id, i.item_img, i.item_update, i.artist, i.director, i.item_point");
		sb.append(" FROM cart c");
		sb.append(" LEFT OUTER JOIN cart_item ci");
		sb.append(" ON ci.cart_id = c.cart_id");
		sb.append(" LEFT OUTER JOIN item i");
		sb.append(" ON ci.item_id = i.item_id");
		sb.append(" WHERE c.user_id = ?");
		sb.append(" ORDER BY ci.item_id");
		
		String sql = sb.toString();
		
		CartEntity cartEntity = jdbcTemplate.query(sql, cartExtractor, userId);
		
		return cartEntity;
	}
}
