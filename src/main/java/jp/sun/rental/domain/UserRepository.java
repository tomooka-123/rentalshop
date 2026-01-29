package jp.sun.rental.domain;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import jp.sun.rental.domain.entity.UserEntity;
import jp.sun.rental.infrastructure.UserRowMapper;

@Repository
public class UserRepository {

	private RowMapper<UserEntity> userMapper = new UserRowMapper();
	private JdbcTemplate jdbcTemplate;
	
	public UserRepository (JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<UserEntity> getUsersAllList() throws Exception {
		
		StringBuilder sb = createCommonSQL();
		sb.append(" ORDER BY user_id");
		String sql = sb.toString();
		
		List<UserEntity> usersList = jdbcTemplate.query(sql, userMapper);
		
		return usersList;
	}
	
	public List<UserEntity> getUsersByName(String name) throws Exception {
		
		StringBuilder sb = createCommonSQL();
		sb.append(" WHERE user_name");
		sb.append( "LIKE ?");
		sb.append(" ORDER BY user_id");
		String sql = sb.toString();
		
		name = name.replaceAll("%", "\\%");
		name = "%" + name + "%";
		
		List<UserEntity> usersList = jdbcTemplate.query(sql, userMapper, name);
		
		return usersList;
	}
	
	public StringBuilder createCommonSQL() {
		
		StringBuilder sb = null;
		
		sb.append("SELECT");
		sb.append(" user_id, user_name, password, email, tell, authority");
		sb.append(" FROM users");
		
		return sb;
	}
}
