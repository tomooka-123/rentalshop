package jp.sun.rental.domain.repository;

import java.util.List;

import javax.swing.tree.RowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
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
		int userId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", int.class);		
		//int userId = getUserIdByEmail(userEntity.getEmail());
		memberEntity.setUserId(userId);
		
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
	
	//emailで検索してuserIdを返す（バリデーションチェック時に使用する）
	public int getUserIdByEmail(String email) throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT user_id FROM users WHERE email = ?");
		String sql = sb.toString();
		int userId = jdbcTemplate.queryForObject(sql, int.class , email);
		return userId;
	}
	
	//userNameで検索してuserIdを返す（バリデーションチェックに使用する）
	public int getUserIdByUserName(String userName) throws Exception{
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT user_id FROM users WHERE user_name = ?");
		String sql = sb.toString();
		int userId = jdbcTemplate.queryForObject(sql, int.class , userName);
		
		return userId;
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
		
		// ユーザー情報更新====================================
		
		public int updateUser( UserUpdateEntity entity) throws Exception{
			
//			ユーザー名
//			メールアドレス
//			電話番号
//			郵便番号
//			住所
//			プラン名
			
			StringBuilder sb = new StringBuilder();		
			sb.append("UPDATE users AS u");
			sb.append("JOIN member AS m ON u.user_id = m.user_id ");
			sb.append("SET ");
			sb.append("u.email = ?,");
			sb.append("u.tell = ?,");
			sb.append("m.post = ?,");
			sb.append("m.address = ?,");
			sb.append("m.plan = ?,");
			sb.append("WHERE u.user_name = ?");
			
			
			String sql = sb.toString();
			
			Object[] parameters = {
					entity.getEmail(),
					entity.getTell(),
					entity.getPost(),
					entity.getAddress(),
					entity.getPlan(),
					entity.getUserName()
			};
			
			int numberOfRow = 0;
			numberOfRow = jdbcTemplate.update(sql,parameters);
			
			return numberOfRow;
		
	}
	
	
}