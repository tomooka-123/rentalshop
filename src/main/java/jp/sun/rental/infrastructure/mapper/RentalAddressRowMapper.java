package jp.sun.rental.infrastructure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import jp.sun.rental.domain.entity.RentalAddressEntity;

public class RentalAddressRowMapper implements RowMapper<RentalAddressEntity> {

	@Override
	public RentalAddressEntity mapRow(ResultSet rs, int numRow)throws SQLException{
		
		RentalAddressEntity address = new RentalAddressEntity();
		
		address.setRentalID(rs.getInt("rental_id"));
		address.setAddress(rs.getString("address"));
		address.setName(rs.getString("name"));
		address.setTell(rs.getString("tell"));
		
		return address;
	}
}
