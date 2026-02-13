package jp.sun.rental.domain.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import jp.sun.rental.domain.entity.CartItemEntity;
import jp.sun.rental.domain.entity.MemberEntity;
import jp.sun.rental.domain.entity.RentalAddressEntity;
import jp.sun.rental.domain.entity.RentalHistoryEntity;
import jp.sun.rental.domain.entity.UserEntity;
import jp.sun.rental.infrastructure.mapper.RentalAddressRowMapper;
import jp.sun.rental.infrastructure.mapper.RentalResultSetExtractor;

@Repository
public class RentalRepository {

	private ResultSetExtractor<List<RentalHistoryEntity>> historyExtractor = new RentalResultSetExtractor();
	private RentalAddressRowMapper rentalAddressRowMapper = new RentalAddressRowMapper();
	private JdbcTemplate jdbcTemplate;
	
	public RentalRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	//ユーザーIDから履歴を検索
	public List<RentalHistoryEntity> getRentalHistoryListByUserId(int userid)throws Exception{
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT");
		sb.append(" r.rental_id, r.user_id, r.rental_date,");
		sb.append(" ri.rental_item_id, ri.rental_id, ri.item_id AS ri_item_id, ri.return_flag,");
		sb.append(" i.item_id AS i_item_id, i.item_name, i.genre_id, i.item_img, i.item_update, i.artist, i.director, i.item_point");
		sb.append(" FROM rental r");
		sb.append(" LEFT OUTER JOIN rental_item ri");
		sb.append(" ON r.rental_id = ri.rental_id");
		sb.append(" LEFT OUTER JOIN item i");
		sb.append(" ON ri.item_id = i.item_id");
		sb.append(" WHERE r.user_id = ?");
		sb.append(" AND (ri.delete_flag = 0 OR ri.delete_flag is NULL)");
		sb.append(" ORDER BY r.rental_date");
		
		String sql = sb.toString();
		
		List<RentalHistoryEntity> historyEntityList = jdbcTemplate.query(sql, historyExtractor, userid);
		
		return historyEntityList;
	}
	
	//ユーザーIDからそのユーザーのレンタル希望履歴の住所などを取得
	public List<RentalAddressEntity> getRentalAddressByUserId(int userId)throws Exception{
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT");
		sb.append(" ra.rental_id, ra.address, ra.name, ra.tell");
		sb.append(" FROM rental_address ra");
		sb.append(" LEFT OUTER JOIN rental r");
		sb.append(" ON ra.rental_id = r.rental_id");
		sb.append(" WHERE r.user_id = ?");
		
		String sql = sb.toString();
		
		List<RentalAddressEntity> addressEntityList = jdbcTemplate.query(sql, rentalAddressRowMapper, userId);
		
		return addressEntityList;
	}
	
	//レンタル履歴登録
	public int registRental(int userid)throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO rental (user_id, rental_date)");
		sb.append(" VALUES (?, NOW())");
		String sql = sb.toString();
		
		int rowRental = 0;
		
		rowRental = jdbcTemplate.update(sql, userid);
		
		return rowRental;
	}
	
	//レンタル商品履歴登録
	public int registRentalItems(int rentalId, CartItemEntity cartItemEntity)throws Exception{
		StringBuilder sb = new StringBuilder();
		
		int rowItems = 0;

		sb.append("INSERT INTO rental_item (rental_id, item_id, return_flag, delete_flag)");
		sb.append(" VALUES (?, ?, 0, 0)");
		
		String sql = sb.toString();
		
		rowItems = jdbcTemplate.update(sql, rentalId, cartItemEntity.getItemId());
		
		return rowItems;
	}
	
	//レンタルした人の現在の住所などを登録
	public int registRentalAddress(int rentalId, UserEntity userEntity, MemberEntity memberEntity)throws Exception{
		StringBuilder sb = new StringBuilder();
		
		int rowAddress = 0;
		
		sb.append("INSERT INTO rental_address (rental_id, address, name, tell)");
		sb.append(" VALUES (?, ?, ?, ?)");
		
		String sql = sb.toString();
		
		rowAddress = jdbcTemplate.update(sql, rentalId, memberEntity.getAddress(), memberEntity.getName(), userEntity.getTell());
		
		return rowAddress;
	}
	
	//返却フラグ切り替え
	public int changeReturnFlag(int rentalItemId)throws Exception{
		StringBuilder sb = new StringBuilder();
		
		sb.append("UPDATE rental_item");
		sb.append(" SET return_flag = NOT return_flag");
		sb.append(" WHERE rental_item_id = ?");
		sb.append(" AND delete_flag = 0");
		
		String sql = sb.toString();
		
		int numRow = 0;
		
		numRow = jdbcTemplate.update(sql, rentalItemId);
		
		return numRow;
	}
	
	//論理削除フラグ切り替え
	public int changeDeleteFlag(int rentalItemId)throws Exception{
		StringBuilder sb = new StringBuilder();
		
		sb.append("UPDATE rental_item");
		sb.append(" SET delete_flag = NOT delete_flag");
		sb.append(" WHERE rental_item_id = ?");
		sb.append(" AND delete_flag = 0");
		String sql = sb.toString();
		
		int numRow = 0;
		
		numRow = jdbcTemplate.update(sql, rentalItemId);
		
		return numRow;
	}
	
	public int getLastInsertId()throws Exception{
		return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", int.class);
	}
}
