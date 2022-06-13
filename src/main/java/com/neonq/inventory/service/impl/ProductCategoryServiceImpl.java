package com.neonq.inventory.service.impl;

import com.neonq.inventory.dao.ProductCategoryDAO;
import com.neonq.inventory.dto.ProductCategoryDTO;
import com.neonq.inventory.model.ProductCategory;
import com.neonq.inventory.service.ProductCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDAO productCategoryDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ProductCategoryDTO> getAllCategories() {
        return productCategoryDAO.findAll().stream().map(a -> modelMapper.map(a, ProductCategoryDTO.class)).collect(Collectors.toList());
    }

    @Override
    public ProductCategoryDTO createProductCategory(ProductCategoryDTO dto) {
        ProductCategory newCategory = productCategoryDAO.save(modelMapper.map(dto, ProductCategory.class));
        return modelMapper.map(newCategory, ProductCategoryDTO.class);
    }
}
