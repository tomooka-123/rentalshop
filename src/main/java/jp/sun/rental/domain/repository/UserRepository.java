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
		sb.append("INSERT INTO users (user_id, user_name, password, email, tell, authority)");
		sb.append(" VALUES (?, ?, ?, ?, ?, ?)");
		String sql = sb.toString();
		
		Object[] parameters = { entity.getUserId(), entity.getUserName(), entity.getPassword(), entity.getEmail(), entity.getTell(), entity.getAuthority()};
		
		int numberOfRow = 0;
		numberOfRow = jdbcTemplate.update(sql,parameters);
		
		return numberOfRow;
	}
	
	//ユーザー情報をmemberテーブルに登録するメソッド
	public int registMember(MemberEntity entity) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO member (user_id, card, user_point, post, address, plan)");
		sb.append(" VALUES (?, ?, ?, ?, ?, ?)");
		String sql = sb.toString();
		
		Object[] parameters = { entity.getUserId(), entity.getCard(), entity.getUserPoint(), entity.getPost(), entity.getAddress(), entity.getPlan()};

		int numberOfRow = 0;
		numberOfRow = jdbcTemplate.update(sql,parameters);
		
		return numberOfRow;
	}
	
	//ユーザー情報を全取得する
	public List<UserEntity> getUsersAllList() throws Exception {
		
		StringBuilder sb = createCommonSQL();
		sb.append(" ORDER BY u.user_id");
		String sql = sb.toString();
		
		List<UserEntity> usersList = jdbcTemplate.query(sql, userMapper);
		
		return usersList;
	}
	
	//入力された名前をもとにデータをあいまい検索する
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
	
	//単一ユーザーを検索する
	public UserEntity getOnlyUserByName(String name) throws Exception {
		
		StringBuilder sb = createCommonSQL();
		sb.append(" WHERE u.user_name = ?");
		String sql = sb.toString();
		
		UserEntity userEntity = jdbcTemplate.queryForObject(sql, userMapper, name);
		
		return userEntity;
	}
	
	//SELECT文のテンプレート記述
	public StringBuilder createCommonSQL() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT");
		sb.append(" u.user_id, u.user_name, u.password, u.email, u.tell, u.authority");
		sb.append(", m.card, m.user_point, m.address, m.post, m.plan, m.name");
		sb.append(" FROM users u");
		sb.append(" LEFT OUTER JOIN member m");
		sb.append(" ON u.user_id = m.user_id");
		
		return sb;
	}
	
	
}