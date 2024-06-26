package com.neonq.inventory.service;

import com.neonq.inventory.dto.orders.OrderItemDTO;
import com.neonq.inventory.dto.PageableProductDTO;
import com.neonq.inventory.dto.ProductDTO;
import com.neonq.inventory.exception.ResourceNotFoundException;
import com.neonq.inventory.model.Product;


public interface ProductService {

    Product getProductById(Long id) ;

    void deleteProduct(Long id);

    PageableProductDTO getProductsPerPage(Integer pageNum, Integer pageSize, String sortBy) ;

    PageableProductDTO getProductsBySearchKeyword(String search, Integer pageNum, Integer pageSize) ;

    ProductDTO createProduct(ProductDTO productDTO) ;

    ProductDTO updateProduct(Long id, ProductDTO productDTO) ;

    ProductDTO orderProduct(String sku, int quantity) ;

    OrderItemDTO orderProductById(Long productId, int quantity) throws ResourceNotFoundException;
}
