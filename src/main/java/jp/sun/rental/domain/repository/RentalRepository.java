package jp.sun.rental.domain.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import jp.sun.rental.domain.entity.RentalHistoryEntity;
import jp.sun.rental.infrastructure.mapper.RentalResultSetExtractor;

@Repository
public class RentalRepository {

	private ResultSetExtractor<RentalHistoryEntity> historyExtractor = new RentalResultSetExtractor();
	private JdbcTemplate jdbcTemplate;
	
	public RentalRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	//ユーザーIDから履歴を検索
	public RentalHistoryEntity getRentalHistoryByUserId(int userid)throws Exception{
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT");
		sb.append(" r.rental_id, r.user_id, r.rentaled_date,");
		sb.append(" ri.rental_item_id, ri.rental_id, ri.item_id, ri.return_flag,");
		sb.append(" i.item_id, i.item_name, i.genre_id, i.item_img, i.item_update, i.artist, i.director, i.item_point");
		sb.append(" FROM rental r");
		sb.append(" LEFT OUTER JOIN rental_item ri");
		sb.append(" ON r.rental_id = ri.rental_id");
		sb.append(" LEFT OUTER JOIN item i");
		sb.append(" ON ri.item_id, i.item_id");
		sb.append(" WHERE r.user_id = ?");
		sb.append(" ORDER BY r.rental_date");
		
		String sql = sb.toString();
		
		RentalHistoryEntity historyEntity = jdbcTemplate.query(sql, historyExtractor, userid);
		
		return historyEntity;
	}
}
