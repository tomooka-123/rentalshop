package jp.sun.rental.application.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.sun.rental.domain.entity.GenreEntity;
import jp.sun.rental.domain.entity.ItemEntity;
import jp.sun.rental.domain.repository.ProductRepository;
import jp.sun.rental.infrastructure.mapper.ItemRegistMapper;
import jp.sun.rental.presentation.form.ItemForm;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final ItemRegistMapper mapper;

    public ProductService(ProductRepository repository, ItemRegistMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ItemEntity> getProductList() {
        return repository.findAll();
    }
    
    public List<ItemEntity> getNewTop5() {
        return repository.findNewTop5();
    }

    public List<ItemEntity> getOldTop5() {
        return repository.findOldTop5();
    }

    public List<ItemEntity> getByGenre(int genre) {
        return repository.findByGenre(genre);
    }
    
    public List<ItemEntity> getRandomByGenre(int genreId) {
        return repository.findByGenreRandom(genreId);
    }
    
    public List<GenreEntity> getCategoryList() {
        return repository.findGenreAll();
    }
    
    @Transactional(rollbackFor = Exception.class)
    public void save(ItemForm itemForm){

    	ItemEntity itemEntity = mapper.toEntity(itemForm);
    	// 日付セット
        itemEntity.setItemUpdate(java.sql.Date.valueOf(LocalDate.now()));

        repository.itemRegist(itemEntity);
    }
}
