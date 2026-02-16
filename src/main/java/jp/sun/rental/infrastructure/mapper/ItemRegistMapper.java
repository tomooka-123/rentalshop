package jp.sun.rental.infrastructure.mapper;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import jp.sun.rental.domain.entity.ItemEntity;
import jp.sun.rental.domain.repository.ProductRepository;
import jp.sun.rental.presentation.form.ItemForm;

@Component
public class ItemRegistMapper {
		
	private final ProductRepository repository;
	
	public ItemRegistMapper(ProductRepository repository) {
        this.repository = repository;
    }
		 
		 // 確定更新用（Form → Entity）
	public ItemEntity toEntity( ItemForm itemForm){

		ItemEntity itemEntity = new ItemEntity();
        
        java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.now());
        
        itemEntity.setItemName(itemForm.getItemName());
        itemEntity.setArtist(itemForm.getArtist());
        itemEntity.setDirector(itemForm.getDirector());
        itemEntity.setGenreId(Integer.valueOf(itemForm.getGenreId()));
        itemEntity.setItemImg(itemForm.getItemImg());
        itemEntity.setItemPoint(Integer.valueOf(itemForm.getItemPoint()));
        itemEntity.setItemUpdate(sqlDate);

		return itemEntity;
	}
}
