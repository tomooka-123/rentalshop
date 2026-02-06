package jp.sun.rental.infrastructure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import jp.sun.rental.domain.entity.CartEntity;
import jp.sun.rental.domain.entity.CartItemEntity;

public class CartRowMapper implements RowMapper<CartEntity> {

	@Override
	public CartEntity mapRow(ResultSet rs, int rowNum) throws SQLException{
		
		CartEntity cartEntity = new CartEntity();
		CartItemEntity cartItemEntity = new CartItemEntity();
		
		cartItemEntity.setCartItemId(rs.getInt("cart_item_id"));
		cartItemEntity.setCartId(rs.getInt("cart_id"));
		cartItemEntity.setItemId(rs.getInt("item_id"));
		
		cartEntity.setCartId(rs.getInt("cart_id"));
		cartEntity.setUserId(rs.getInt("user_id"));
		cartEntity.setCartItems(cartItemEntity);
		
		return cartEntity;
	}
}
