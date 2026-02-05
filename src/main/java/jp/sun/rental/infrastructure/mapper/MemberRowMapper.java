package jp.sun.rental.infrastructure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import jp.sun.rental.domain.entity.MemberEntity;

public class MemberRowMapper implements RowMapper<MemberEntity>{

	@Override
	public MemberEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		MemberEntity member = new MemberEntity();
		
		member.setUserId(rs.getInt("user_id"));
		member.setUserPoint(rs.getInt("user_point"));
		member.setCard(rs.getString("card"));
		member.setName(rs.getString("name"));
		member.setPost(rs.getString("user_post"));
		member.setAddress(rs.getString("address"));
		member.setPlan(rs.getString("password"));
		
		return member;
	}
}
