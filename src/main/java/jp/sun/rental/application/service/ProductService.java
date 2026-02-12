package jp.sun.rental.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jp.sun.rental.domain.entity.ItemEntity;
import jp.sun.rental.domain.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
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

}
