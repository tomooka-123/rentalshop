package jp.sun.rental.infrastructure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import jp.sun.rental.domain.entity.ItemEntity;
import jp.sun.rental.domain.entity.RentalHistoryEntity;
import jp.sun.rental.domain.entity.RentalItemEntity;

public class RentalResultSetExtractor implements ResultSetExtractor<RentalHistoryEntity> {

	@Override
	public RentalHistoryEntity extractData(ResultSet rs)throws SQLException{
		
		RentalHistoryEntity historyEntity = null;
		List<RentalItemEntity> rentalItems = new ArrayList<RentalItemEntity>();
		
		while(rs.next()) {
			if(historyEntity == null) {
				historyEntity = new RentalHistoryEntity();
				historyEntity.setRentalId(rs.getInt("rental_id"));
				historyEntity.setUserId(rs.getInt("user_id"));
				historyEntity.setRentalDate(rs.getDate("rentaled_date"));
			}
			
			rs.getInt("rental_item_id");
			while(!rs.wasNull()) {
				RentalItemEntity rentalItemEntity = new RentalItemEntity();
				ItemEntity itemEntity = new ItemEntity();
				
				itemEntity.setItemId(rs.getInt("item_id"));
				itemEntity.setItemName(rs.getString("item_name"));
				itemEntity.setGenreId(rs.getInt("genre_id"));
				itemEntity.setItemImg(rs.getString("item_img"));
				itemEntity.setItemUpdate(rs.getDate("item_update"));
				itemEntity.setArtist(rs.getString("artist"));
				itemEntity.setDirector(rs.getString("director"));
				itemEntity.setItemPoint(rs.getInt("item_point"));
				
				rentalItemEntity.setRetalItemId(rs.getInt("rental_item_id"));
				rentalItemEntity.setRentalId(rs.getInt("rental_id"));
				rentalItemEntity.setItemId(rs.getInt("item_id"));
				rentalItemEntity.setReturnFlag(rs.getInt("return_flag"));
				rentalItemEntity.setItemEntity(itemEntity);
				
				rentalItems.add(rentalItemEntity);
			}
		}
		if(historyEntity != null) {
			historyEntity.setRentalItems(rentalItems);
		}
		return historyEntity;
	}
}
