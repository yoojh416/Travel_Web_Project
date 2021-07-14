package com.upload.files.service;

import com.upload.files.entity.ListSearch;
import com.upload.files.entity.Product;
import com.upload.files.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public void save(Product product) {
        productRepository.save(product);
    }

    public List<Product> products() {
        return productRepository.findAll();
    }

    public Product findOne(Long proNo) {
        return productRepository.findOne(proNo);
    }

    public List<Product> findItemsByFilter(ListSearch listSearch) {
        return productRepository.findByFilter(listSearch);
    }

}
