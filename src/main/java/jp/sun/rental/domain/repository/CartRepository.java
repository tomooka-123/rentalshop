package jp.sun.rental.domain.repository;

import org.springframework.dao.EmptyResultDataAccessException;
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
	
	//ユーザーIDからカートIDを取得する
	public int getCartId(int userId) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT cart_id FROM cart WHERE user_id = ?");
		String sql = sb.toString();
		int cartId;
		
		try {
			cartId = jdbcTemplate.queryForObject(sql, int.class, userId);
		} catch (EmptyResultDataAccessException e) {
			// カートが無ければ作成
			jdbcTemplate.update("INSERT INTO cart(user_id) VALUES(?)", userId);
			cartId = jdbcTemplate.queryForObject(sql, int.class, userId);
	    }
		
		return cartId;
	}
	
	//商品をcart_itemに追加
	public int addCart(int itemId, int cartId) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO cart_item (item_id, cart_id)");
		sb.append(" VALUES (?, ?)");
		String sql = sb.toString();
		
		int numberOfRow = jdbcTemplate.update(sql,itemId, cartId);
		return numberOfRow;
	}

	//受け取ったユーザーIDのカートの中身を全削除
	public int deleteCartItemsByUserId(int userId) throws Exception{
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE");
		sb.append(" ci FROM cart_item ci");
		sb.append(" INNER JOIN cart c");
		sb.append(" ON ci.cart_id = c.cart_id");
		sb.append(" WHERE c.user_id = ?");
		
		String sql = sb.toString();
		
		int numOfRow = 0;
		
		numOfRow = jdbcTemplate.update(sql, userId);
		
		return numOfRow;
	}

}
