package jp.sun.rental.domain.repository;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.sun.rental.domain.entity.ItemEntity;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ItemEntity> findAll() {

        String sql = """
            SELECT item_id, item_name, genre_id, item_img AS itemImg, item_update, artist, director, item_point
            FROM item
            ORDER BY item_id DESC
        """;

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(ItemEntity.class)
        );
    }
}
