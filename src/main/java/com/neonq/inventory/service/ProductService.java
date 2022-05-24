package com.neonq.inventory.service;

import com.neonq.inventory.dao.ProductDAO;
import com.neonq.inventory.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductDAO productDAO;

    public Optional<Product> getProduct(long id) {
        return productDAO.findById(id);
    }

    public Product createOrUpdateProduct(Product product) {
        return productDAO.save(product);
    }

    public boolean deleteProduct(Product product) {
        productDAO.delete(product);
        return true;
    }

    public Page<Product> getProductsBySearch(String search) {
        return productDAO.findByNameContainingIgnoreCaseLike(search, PageRequest.of(0,6));

    }
}
