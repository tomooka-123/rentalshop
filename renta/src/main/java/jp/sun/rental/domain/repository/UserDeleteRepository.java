package jp.sun.rental.domain.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDeleteRepository {
	
	private JdbcTemplate jdbcTemplate;

	public UserDeleteRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public int deleteByUserId(Long userId) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE");
		sb.append(" FROM users");
		sb.append(" WHERE user_id = ?");
		String sql = sb.toString();
		
		return jdbcTemplate.update(sql, userId);
    }
}
