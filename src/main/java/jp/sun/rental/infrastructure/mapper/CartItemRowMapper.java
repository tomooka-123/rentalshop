package jp.sun.rental.infrastructure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import jp.sun.rental.domain.entity.CartEntity;
import jp.sun.rental.domain.entity.CartItemEntity;

public class CartItemRowMapper implements RowMapper<CartItemEntity> {

	@Override
	public CartItemEntity mapRow(ResultSet rs, int rowNum) throws SQLException{
		
		CartItemEntity cartItemEntity = new CartItemEntity();
		CartEntity cartEntity = new CartEntity();
		
		cartEntity.setCartId(rs.getInt("cart_id"));
		
		cartItemEntity.setCartItemId(rs.getInt("cart_item_id"));
		cartItemEntity.setItemId(rs.getInt("item_id"));
		cartItemEntity.setCartEntity(cartEntity);
		
		
		return cartItemEntity;
	}
}
