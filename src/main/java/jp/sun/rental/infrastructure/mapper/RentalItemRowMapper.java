package jp.sun.rental.infrastructure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import jp.sun.rental.domain.entity.ItemEntity;
import jp.sun.rental.domain.entity.RentalItemEntity;

public class RentalItemRowMapper implements RowMapper<RentalItemEntity> {

	@Override
	public RentalItemEntity mapRow(ResultSet rs, int rowNum)throws SQLException{
		RentalItemEntity rentalItem = new RentalItemEntity();
		ItemEntity itemEntity = new ItemEntity();
		
		itemEntity.setItemId(rs.getInt("i_item_id"));
		itemEntity.setItemName(rs.getString("item_name"));
		itemEntity.setGenreId(rs.getInt("genre_id"));
		itemEntity.setItemImg(rs.getString("item_img"));
		itemEntity.setItemUpdate(rs.getDate("item_update"));
		itemEntity.setArtist(rs.getString("artist"));
		itemEntity.setDirector(rs.getString("director"));
		itemEntity.setItemPoint(rs.getInt("item_point"));
		
		rentalItem.setRentalItemId(rs.getInt("rental_item_id"));
		rentalItem.setRentalId(rs.getInt("rental_id"));
		rentalItem.setItemId(rs.getInt("ri_item_id"));
		rentalItem.setReturnFlag(rs.getBoolean("return_flag"));
		rentalItem.setItemEntity(itemEntity);
		
		return rentalItem;
	}
}
