package jp.sun.rental.domain.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import jp.sun.rental.domain.entity.CartItemEntity;
import jp.sun.rental.infrastructure.mapper.CartItemRowMapper;

@Repository
public class CartRepository {

	private RowMapper<CartItemEntity> cartItemMapper = new CartItemRowMapper();
	private JdbcTemplate jdbcTemplate;
	
	public CartRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	//ユーザーIDからカートの中身を検索
	public List<CartItemEntity> getCartItemsListByUserId(String userId) throws Exception{
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT");
		sb.append(" ci.cart_item_id, ci.item_id, ci.cart_id");
		sb.append(" FROM cart_item ci");
		sb.append(" JOIN cart c");
		sb.append(" ON ci.cart_id = c.cart_id");
		sb.append(" WHERE c.user_id = ?");
		sb.append(" ORDER BY ci.item_id");
		
		String sql = sb.toString();
		
		List<CartItemEntity> cartItemsList = jdbcTemplate.query(sql, cartItemMapper, userId);
		
		return cartItemsList;
	}
}
