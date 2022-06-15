package com.neonq.inventory.service;

import com.neonq.inventory.dto.PageableProductDTO;
import com.neonq.inventory.dto.ProductDTO;
import com.neonq.inventory.exception.ResourceNotFoundException;
import com.neonq.inventory.model.Product;
import com.neonq.inventory.model.ProductCategory;


public interface ProductService {

    Product getProductById(Long id) throws ResourceNotFoundException;

    void deleteProduct(Long id);

    PageableProductDTO getProductsPerPage(Integer pageNum, Integer pageSize, String sortBy) ;

    PageableProductDTO getProductsBySearchKeyword(String search, Integer pageNum, Integer pageSize) ;

    ProductDTO createProduct(ProductDTO productDTO) throws ResourceNotFoundException;

    ProductDTO updateProduct(Long id, ProductDTO productDTO) throws ResourceNotFoundException;
}
