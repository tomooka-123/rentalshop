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
		sb1.append("DELETE FROM member WHERE user_id = ?");
		String sql1 = sb1.toString();
		int resultMember = jdbcTemplate.update(sql1, userId);
		
		StringBuilder sb2 = new StringBuilder();
		sb2.append("DELETE FROM users WHERE user_id = ?");
		String sql2 = sb2.toString();
		int resultUser = jdbcTemplate.update(sql2, userId);
		
		return resultMember + resultUser;
    }
	
	public int findUserIdByUserName(String userName){
		StringBuilder sb3 = new StringBuilder();
		sb3.append("SELECT user_id FROM users WHERE user_name = ?");
		String sql3 = sb3.toString();
		int resultNumber = jdbcTemplate.queryForObject(sql3,Integer.class,userName);
		
		return resultNumber;
	}
}