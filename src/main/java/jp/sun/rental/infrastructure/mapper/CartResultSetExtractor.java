package jp.sun.rental.infrastructure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import jp.sun.rental.domain.entity.CartEntity;
import jp.sun.rental.domain.entity.CartItemEntity;
import jp.sun.rental.domain.entity.ItemEntity;

public class CartResultSetExtractor implements ResultSetExtractor<CartEntity> {
	
	@Override
	public CartEntity extractData(ResultSet rs) throws SQLException{
		
		CartEntity cartEntity = null;
		List<CartItemEntity> cartItems = new ArrayList<CartItemEntity>();
		
		while(rs.next()) {
			if(cartEntity == null) {
				cartEntity = new CartEntity();
				cartEntity.setCartId(rs.getInt("cart_id"));
				cartEntity.setUserId(rs.getInt("user_id"));
			}
			
			//商品格納用カートのデータが存在するかどうか
			rs.getInt("cart_item_id");
			if(!rs.wasNull()) {
				CartItemEntity cartItemEntity = new CartItemEntity();
				ItemEntity itemEntity = new ItemEntity();
				
				itemEntity.setItemId(rs.getInt("item_id"));
				itemEntity.setItemName(rs.getString("item_name"));
				itemEntity.setGenreId(rs.getInt("genre_id"));
				itemEntity.setItemImg(rs.getString("item_img"));
				itemEntity.setItemUpdate(rs.getDate("item_update"));
				itemEntity.setArtist(rs.getString("artist"));
				itemEntity.setDirector(rs.getString("director"));
				itemEntity.setItemPoint(rs.getInt("item_point"));
				
				cartItemEntity.setCartItemId(rs.getInt("cart_item_id"));
				cartItemEntity.setItemId(rs.getInt("item_id"));
				cartItemEntity.setItemEntity(itemEntity);
			}
		}
		if(cartEntity != null) {
			cartEntity.setCartItems(cartItems);
		}
		
		return cartEntity;
	}
}
