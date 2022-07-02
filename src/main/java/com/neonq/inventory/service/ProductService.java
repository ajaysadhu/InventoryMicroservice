package com.neonq.inventory.service;

import com.neonq.inventory.dao.ProductDAO;
import com.neonq.inventory.dto.PageableProductDTO;
import com.neonq.inventory.dto.ProductDTO;
import com.neonq.inventory.exception.ResourceNotFoundException;
import com.neonq.inventory.model.Product;
import com.neonq.inventory.model.ProductCategory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import java.sql.SQLException;


public interface ProductService extends Runnable {

    Product getProductById(Long id) ;

    void deleteProduct(Long id);

    PageableProductDTO getProductsPerPage(Integer pageNum, Integer pageSize, String sortBy) ;

    PageableProductDTO getProductsBySearchKeyword(String search, Integer pageNum, Integer pageSize) ;

    ProductDTO createProduct(ProductDTO productDTO) ;

    ProductDTO updateProduct(Long id, ProductDTO productDTO) ;


    ProductDTO orderProduct(String sku, int quntity) ;
}
