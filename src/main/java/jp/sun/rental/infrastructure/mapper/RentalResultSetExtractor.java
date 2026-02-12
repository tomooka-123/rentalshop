package jp.sun.rental.infrastructure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import jp.sun.rental.domain.entity.RentalHistoryEntity;
import jp.sun.rental.domain.entity.RentalItemEntity;

public class RentalResultSetExtractor implements ResultSetExtractor<List<RentalHistoryEntity>> {

	private RentalHistoryRowMapper rentalHistoryRowMapper = new RentalHistoryRowMapper();
	private RentalItemRowMapper rentalItemRowMapper = new RentalItemRowMapper();
	
	@Override
	public List<RentalHistoryEntity> extractData(ResultSet rs)throws SQLException{
		
		List<RentalHistoryEntity> historiesList = new ArrayList<RentalHistoryEntity>();
		RentalHistoryEntity historyEntity = null;
		int tmpRentalId = 0;
		
		while(rs.next()) {
			if(tmpRentalId != rs.getInt("rental_id")) {
				historyEntity = rentalHistoryRowMapper.mapRow(rs, 0);
				historyEntity.setRentalItems(new ArrayList<RentalItemEntity>());
				historiesList.add(historyEntity);
			}
			
			rs.getInt("rental_item_id");
			if(!rs.wasNull()) {
				RentalItemEntity rentalItem = rentalItemRowMapper.mapRow(rs, 0);
				historyEntity.getRentalItems().add(rentalItem);
			}
			tmpRentalId = rs.getInt("rental_id");
		}
		return historiesList;
	}
}
