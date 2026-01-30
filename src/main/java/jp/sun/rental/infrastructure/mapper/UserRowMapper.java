package jp.sun.rental.infrastructure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import jp.sun.rental.domain.entity.MemberEntity;
import jp.sun.rental.domain.entity.UserEntity;

public class UserRowMapper implements RowMapper<UserEntity> {

	@Override
	public UserEntity mapRow (ResultSet rs, int rowNum) throws SQLException{
		UserEntity userEntity = new UserEntity();
		MemberEntity memberEntity = new MemberEntity();
		
		memberEntity.setUserId(rs.getInt("user_id"));
		memberEntity.setCard(rs.getInt("card"));
		memberEntity.setUserPoint(rs.getInt("user_point"));
		memberEntity.setAddress(rs.getString("address"));
		memberEntity.setPost(rs.getString("post"));
		memberEntity.setPlan(rs.getString("plan"));
		
		userEntity.setUserId(rs.getInt("user_id"));
		userEntity.setUserName(rs.getString("user_name"));
		userEntity.setPassword(rs.getString("password"));
		userEntity.setEmail(rs.getString("email"));
		userEntity.setTell(rs.getString("tell"));
		userEntity.setAuthority(rs.getString("authority"));
		userEntity.setMembers(memberEntity);
		
		
		return userEntity;
		
	}
}
