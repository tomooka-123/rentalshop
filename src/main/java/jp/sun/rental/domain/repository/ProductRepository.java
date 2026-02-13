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
    
    public List<ItemEntity> findNewTop5() {

        String sql = """
            SELECT *
            FROM item
            ORDER BY item_update DESC
            LIMIT 5
        """;

        return jdbcTemplate.query(sql,
            new BeanPropertyRowMapper<>(ItemEntity.class));
    }
    
    public List<ItemEntity> findOldTop5() {

        String sql = """
            SELECT *
            FROM item
            ORDER BY item_point DESC
            LIMIT 5
        """;

        return jdbcTemplate.query(sql,
            new BeanPropertyRowMapper<>(ItemEntity.class));
    }
    
    public List<ItemEntity> findByGenre(int genre) {

        String sql = """
            SELECT *
            FROM item
            WHERE genre_id = ?
            ORDER BY item_id DESC
            LIMIT 5
        """;

        return jdbcTemplate.query(sql,
            new BeanPropertyRowMapper<>(ItemEntity.class),
            genre);
    }

    public List<ItemEntity> findByGenreRandom(int genreId) {

        String sql = """
            SELECT *
            FROM item
            WHERE genre_id = ?
            ORDER BY RAND()
            LIMIT 5
        """;

        return jdbcTemplate.query(
            sql,
            new BeanPropertyRowMapper<>(ItemEntity.class),
            genreId
        );
    }


}
