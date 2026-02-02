package jp.sun.rental.domain.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import jp.sun.rental.domain.entity.MemberEntity;
import jp.sun.rental.domain.entity.UserEntity;
import jp.sun.rental.infrastructure.mapper.UserRowMapper;

@Repository
public class UserRepository {
	
	//フィールド
	private RowMapper<UserEntity> userMapper = new UserRowMapper();
	private JdbcTemplate jdbcTemplate;
	
	//コンストラクター
	public UserRepository (JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	//ユーザー情報をusersテーブルに登録するメソッド
	public int registUser(UserEntity entity) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO users (user_name, password, email, tell, authority)");
		sb.append(" VALUES (?, ?, ?, ?, ?)");
		String sql = sb.toString();
		
		Object[] parameters = { entity.getUserName(), entity.getPassword(), entity.getEmail(), entity.getTell(), entity.getAuthority()};
		
		int numberOfRow = 0;
		numberOfRow = jdbcTemplate.update(sql,parameters);
		
		return numberOfRow;
	}
	
	//ユーザー情報をmemberテーブルに登録するメソッド
	public int registMember(MemberEntity entity) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO member (card, user_point, post, address, plan, name)");
		sb.append(" VALUES (?, ?, ?, ?, ?, ?)");
		String sql = sb.toString();
		
		Object[] parameters = { entity.getCard(), entity.getUserPoint(), entity.getPost(), entity.getAddress(), entity.getPlan(), entity.getName()};

		int numberOfRow = 0;
		numberOfRow = jdbcTemplate.update(sql,parameters);
		
		return numberOfRow;
	}
	
	
	public List<UserEntity> getUsersAllList() throws Exception {
		
		StringBuilder sb = createCommonSQL();
		sb.append(" ORDER BY u.user_id");
		String sql = sb.toString();
		
		List<UserEntity> usersList = jdbcTemplate.query(sql, userMapper);
		
		return usersList;
	}
	
	public List<UserEntity> getUsersByName(String name) throws Exception {
		
		StringBuilder sb = createCommonSQL();
		sb.append(" WHERE u.user_name");
		sb.append(" LIKE ?");
		sb.append(" ORDER BY u.user_id");
		String sql = sb.toString();
		
		name = name.replaceAll("%", "\\%");
		name = "%" + name + "%";
		
		List<UserEntity> usersList = jdbcTemplate.query(sql, userMapper, name);
		
		return usersList;
	}
	
	public StringBuilder createCommonSQL() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT");
		sb.append(" u.user_id, u.user_name, u.password, u.email, u.tell, u.authority");
		sb.append(", m.card, m.user_point, m.address, m.post, m.plan");
		sb.append(" FROM users u");
		sb.append(" JOIN member m");
		sb.append(" ON u.user_id = m.user_id");
		
		return sb;
	}
	
	
}