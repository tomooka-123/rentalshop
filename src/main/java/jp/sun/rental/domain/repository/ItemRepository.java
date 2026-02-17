package jp.sun.rental.domain.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import jp.sun.rental.domain.entity.ItemEntity;
import jp.sun.rental.infrastructure.mapper.ItemRowMapper;

@Repository
public class ItemRepository {

	private RowMapper<ItemEntity> userMapper = new ItemRowMapper();
	private JdbcTemplate jdbcTemplate;
	
	public ItemRepository (JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	//商品情報を全取得する
	public List<ItemEntity> getItemsAllList() throws Exception {
		
		StringBuilder sb = createCommonSQL();
		sb.append(" ORDER BY item_id");
		String sql = sb.toString();
		
		List<ItemEntity> itemsList = jdbcTemplate.query(sql, userMapper);
		
		return itemsList;
	}
	
	//入力された商品名をもとにデータをあいまい検索する
	public List<ItemEntity> getItemsByName(String name) throws Exception {
		
		StringBuilder sb = createCommonSQL();
		sb.append(" WHERE item_name");
		sb.append(" LIKE ?");
		sb.append(" ORDER BY item_id");
		String sql = sb.toString();
		
		name = name.replaceAll("%", "\\%");
		name = "%" + name + "%";
		
		List<ItemEntity> itemsList = jdbcTemplate.query(sql, userMapper, name);
		
		return itemsList;
	}
	
	
	//商品IDで商品データを検索
	public ItemEntity getItem(Integer itemId) {
		StringBuilder sb = createCommonSQL();
		sb.append(" WHERE item_id = ?");
		String sql = sb.toString();
		
		ItemEntity entity = jdbcTemplate.queryForObject(sql, userMapper, itemId);
		
		return entity;
	}
	
	
	
	//SELECT文のテンプレート記述
	public StringBuilder createCommonSQL() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT");
		sb.append(" item_id, item_name, genre_id, item_img, item_update, artist, director, item_point");
		sb.append(" FROM item");
		
		return sb;
	}
}
