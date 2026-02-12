package jp.sun.rental.domain.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import jp.sun.rental.domain.entity.CartItemEntity;
import jp.sun.rental.domain.entity.RentalHistoryEntity;
import jp.sun.rental.infrastructure.mapper.RentalResultSetExtractor;

@Repository
public class RentalRepository {

	private ResultSetExtractor<List<RentalHistoryEntity>> historyExtractor = new RentalResultSetExtractor();
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
		sb.append(" ORDER BY r.rental_date");
		
		String sql = sb.toString();
		
		List<RentalHistoryEntity> historyEntityList = jdbcTemplate.query(sql, historyExtractor, userid);
		
		return historyEntityList;
	}
	
	public int registRental(int userid)throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO rental (user_id, rental_date)");
		sb.append(" VALUES (?, NOW())");
		String sql = sb.toString();
		
		int rowRental = 0;
		
		rowRental = jdbcTemplate.update(sql, userid);
		
		return rowRental;
	}
	
	public int registRentalItems(int rentalId, CartItemEntity cartItemEntity)throws Exception{
		StringBuilder sb = new StringBuilder();
		
		int rowItems = 0;
		
		sb = new StringBuilder();
		sb.append("INSERT INTO rental_item (rental_id, item_id, return_flag");
		sb.append(" VALUES (? ? 0)");
		
		String sql = sb.toString();
		
		rowItems = jdbcTemplate.update(sql, rentalId, cartItemEntity.getItemId());
		
		return rowItems;
	}
	
	public int getLastInsertId()throws Exception{
		return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", int.class);
	}
}
