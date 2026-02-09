package jp.sun.rental.domain.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDeleteRepository {
	
	private JdbcTemplate jdbcTemplate;

	public UserDeleteRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public int deleteByUserId(int userId) {

		StringBuilder sb1 = new StringBuilder();
		sb1.append("UPDATE users SET is_deleted = TRUE WHERE user_id = ?");
		String sql1 = sb1.toString();
		int resultUser = jdbcTemplate.update(sql1, userId);
		
		return resultUser;
    }
	
	public int findUserIdByUserName(String userName){
		StringBuilder sb2 = new StringBuilder();
		sb2.append("SELECT user_id FROM users WHERE user_name = ?");
		sb2.append(" AND is_deleted = FALSE");
		String sql2 = sb2.toString();
		int resultNumber = jdbcTemplate.queryForObject(sql2,Integer.class,userName);
		
		return resultNumber;
	}
}
