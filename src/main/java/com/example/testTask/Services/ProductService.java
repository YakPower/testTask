package com.example.testTask.Services;
import com.example.testTask.Entities.Product;
import com.example.testTask.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }



    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
}
