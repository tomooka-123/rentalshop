package jp.sun.rental.infrastructure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import jp.sun.rental.domain.entity.RentalHistoryEntity;

public class RentalHistoryRowMapper implements RowMapper<RentalHistoryEntity> {

	@Override
	public RentalHistoryEntity mapRow(ResultSet rs, int rowNum)throws SQLException{
		
		RentalHistoryEntity history = new RentalHistoryEntity();
		
		history.setRentalId(rs.getInt("rental_id"));
		history.setUserId(rs.getInt("user_id"));
		history.setRentalDate(rs.getDate("rental_date"));
		history.setAddress(rs.getString("address"));
		history.setAddressName(rs.getString("address_name"));
		
		history.setRentalItems(null);
		
		return history;
	}
}
