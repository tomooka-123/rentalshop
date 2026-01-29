package jp.sun.rental.domain.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import jp.sun.rental.domain.entity.UserEntity;
import jp.sun.rental.infrastructure.mapper.UserRowMapper;

@Repository
public class UserRepository {

	private RowMapper<UserEntity> userMapper = new UserRowMapper();
	private JdbcTemplate jdbcTemplate;
	
	public UserRepository (JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	//ユーザー情報をDBに登録するメソッド
	public int regist(UserEntity entity) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO users (user_id, user_name, password, email, tell, authority)");
		sb.append("VALUES (?, ?, ?, ?, ?, ?)");
		String sql = sb.toString();
		
		Object[] parameters = { entity.getUserId(), entity.getUserName(), entity.getPassword(), entity.getEmail(), entity.getTell(), entity.getAuthority()};

		int numberOfRow = 0;
		numberOfRow = jdbcTemplate.update(sql,parameters);
		
		return numberOfRow;
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
	
	@SuppressWarnings("null")
	public StringBuilder createCommonSQL() {
		
		StringBuilder sb = null;
		
		sb.append("SELECT");
		sb.append(" user_id, user_name, password, email, tell, authority");
		sb.append(" FROM users");
		
		return sb;
	}
}
