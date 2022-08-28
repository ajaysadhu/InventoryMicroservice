package com.neonq.inventory.service;

import com.neonq.inventory.dto.ProductCategoryDTO;
import com.neonq.inventory.exception.ResourceAlreadyExistsException;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategoryDTO> getAllCategories();

    ProductCategoryDTO createProductCategory(ProductCategoryDTO dto) throws ResourceAlreadyExistsException;

    void deleteProductCategory(Long id);
}
