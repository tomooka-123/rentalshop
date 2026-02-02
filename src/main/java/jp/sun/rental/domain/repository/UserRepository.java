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
	
	//ユーザー情報を2つのテーブルに登録するメソッド
	public int regist(UserEntity userEntity, MemberEntity memberEntity) throws Exception {
		//usersテーブルに登録する
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO users (user_name, password, email, tell, authority)");
		sb.append(" VALUES (?, ?, ?, ?, ?)");
		String sql = sb.toString();
		
		Object[] userParameters = { userEntity.getUserName(), userEntity.getPassword(), userEntity.getEmail(), userEntity.getTell(), userEntity.getAuthority()};
		
		int resultUser = 0;
		resultUser = jdbcTemplate.update(sql,userParameters);
		
		//usersテーブルからuser_idを取得する
		sb = new StringBuilder();
		sb.append("SELECT user_id FROM users WHERE email = ?");
		sql = sb.toString();
		int id = jdbcTemplate.queryForObject(sql, int.class , userEntity.getEmail());
		memberEntity.setUserId(id);
		
		//memberテーブルに登録する
		sb = new StringBuilder();
		sb.append("INSERT INTO member (user_id, card, user_point, post, address, plan, name)");
		sb.append(" VALUES (?, ?, ?, ?, ?, ?, ?)");
		sql = sb.toString();
		
		Object[] memberParameters = { memberEntity.getUserId(), memberEntity.getCard(), memberEntity.getUserPoint(), memberEntity.getPost(), memberEntity.getAddress(), memberEntity.getPlan(), memberEntity.getName()};

		int resultMember = 0;
		resultMember = jdbcTemplate.update(sql,memberParameters);
		
		int numberOfRow = resultUser + resultMember;
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