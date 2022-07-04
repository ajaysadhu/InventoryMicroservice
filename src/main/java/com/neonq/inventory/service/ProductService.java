package com.neonq.inventory.service;

import com.neonq.inventory.dto.PageableProductDTO;
import com.neonq.inventory.dto.ProductDTO;
import com.neonq.inventory.model.Product;
import org.springframework.retry.annotation.Retryable;

import java.util.concurrent.CompletableFuture;


public interface ProductService extends Runnable {

    Product getProductById(Long id) ;

    void deleteProduct(Long id);

    PageableProductDTO getProductsPerPage(Integer pageNum, Integer pageSize, String sortBy) ;

    PageableProductDTO getProductsBySearchKeyword(String search, Integer pageNum, Integer pageSize) ;

    ProductDTO createProduct(ProductDTO productDTO) ;

    ProductDTO updateProduct(Long id, ProductDTO productDTO) ;

    CompletableFuture<ProductDTO> orderProduct(String sku, int quantity) ;

    // ProductDTO orderProduct(String sku, int quantity) ;

    @Retryable(include = Exception.class, exclude = IllegalStateException.class,
            maxAttempts = 5)
    ProductDTO retryableTestMethod(String skuName);

    String orderProductById(Long productId, int quantity) throws InterruptedException;
}
