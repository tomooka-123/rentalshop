package jp.sun.rental.infrastructure;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import jp.sun.rental.domain.entity.UserEntity;

public class UserRowMapper implements RowMapper<UserEntity> {

	@Override
	public UserEntity mapRow (ResultSet rs, int rowNum) throws SQLException{
		UserEntity userEntity = new UserEntity();
		
		userEntity.setUserId(rs.getInt("user_id"));
		userEntity.setUserName(rs.getString("user_name"));
		userEntity.setPassword(rs.getString("password"));
		userEntity.setEmail(rs.getString("email"));
		userEntity.setTell(rs.getString("tell"));
		userEntity.setAuthority(rs.getString("authority"));
		
		return userEntity;
		
	}
}
