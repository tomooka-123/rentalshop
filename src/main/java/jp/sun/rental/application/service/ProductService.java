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
}
