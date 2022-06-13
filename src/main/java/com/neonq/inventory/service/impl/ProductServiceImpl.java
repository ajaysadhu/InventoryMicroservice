package com.neonq.inventory.service.impl;

import com.neonq.inventory.dao.ProductCategoryDAO;
import com.neonq.inventory.dao.ProductDAO;
import com.neonq.inventory.dto.PageableProductDTO;
import com.neonq.inventory.dto.ProductDTO;
import com.neonq.inventory.exception.ResourceNotFoundException;
import com.neonq.inventory.model.Product;
import com.neonq.inventory.model.ProductCategory;
import com.neonq.inventory.service.ProductService;
import com.neonq.inventory.utils.CommonUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDAO productDAO;

    @Autowired
    ProductCategoryDAO productCategoryDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Product getProductById(Long id) throws ResourceNotFoundException {
        Optional<Product> product = productDAO.findById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new ResourceNotFoundException("Product Id does not exist");
        }
    }

    public void deleteProduct(Long id) {
        productDAO.deleteById(id);
    }

    @Override
    public PageableProductDTO getProductsPerPage(Integer pageNum, Integer pageSize, String sortBy) {
        Pageable pageable = (StringUtils.hasText(sortBy))
                ? PageRequest.of(pageNum, CommonUtils.getPageDisplayCount(pageSize),Sort.by(sortBy))
                : PageRequest.of(pageNum, CommonUtils.getPageDisplayCount(pageSize));
        PageableProductDTO result = new PageableProductDTO();
        Page<Product> productPage = productDAO.findAll(pageable);
        Type listType = new TypeToken<List<ProductDTO>>() {}.getType();
        result.setProductDTOList(modelMapper.map(productPage.toList(), listType));
        result.setTotalRecords(productPage.getTotalElements());
        result.setTotalPages(productPage.getTotalPages());
        result.setCurrentPage(pageNum+1);
        return result;
    }

    public PageableProductDTO getProductsBySearchKeyword(String search, Integer pageNum, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNum, CommonUtils.getPageDisplayCount(pageSize));
        Page<Product> productPage = productDAO.findByKeyword(search, paging);
        if (productPage == null && productPage.getTotalElements() == 0) {
            return new PageableProductDTO();
        }
        PageableProductDTO result = new PageableProductDTO();
        Type listType = new TypeToken<List<ProductDTO>>() {}.getType();
        result.setProductDTOList(modelMapper.map(productPage.toList(), listType));
        result.setTotalRecords(productPage.getTotalElements());
        result.setTotalPages(productPage.getTotalPages());
        result.setCurrentPage(pageNum+1);
        return result;
    }

    public ProductDTO createProduct(ProductDTO productDTO) throws ResourceNotFoundException {
        // TODO Error handling for id
        Optional<ProductCategory> category = productCategoryDAO.findById(productDTO.getCategory().getId());
        if(!category.isPresent()) {
            throw new ResourceNotFoundException("Category ID doesn't exist");
        }

        modelMapper.typeMap(ProductDTO.class, Product.class).addMappings(mapper -> {
            // not setting category instead use the optional
            mapper.skip(Product::setCategory);
        });

        Product newProduct = modelMapper.map(productDTO, Product.class);
        newProduct.setCategory(category.get());

        return modelMapper.map( productDAO.save(newProduct), ProductDTO.class);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) throws ResourceNotFoundException {

        Optional<Product> existingOptional = productDAO.findById(id);
        Optional<ProductCategory> categoryOptional = productCategoryDAO.findById(productDTO.getCategory().getId());
        productCategoryDAO.flush();
        System.out.println("Product To Save 1  :: " + categoryOptional.get() );
        if(!existingOptional.isPresent()) {
            throw new ResourceNotFoundException("Product Id passed doesn't exist");
        }
        if(!categoryOptional.isPresent()) {
            throw new ResourceNotFoundException("Product Category Id passed doesn't exist");
        }
        ProductCategory newProductCategory = categoryOptional.get();
        Product productToUpdate = existingOptional.get();
        modelMapper.typeMap(ProductDTO.class, Product.class).addMappings(mapper -> {
            // not setting category instead use the optional
            mapper.skip(Product::setCategory);
            mapper.skip(Product::setId);
        });
        productToUpdate.setCategory(newProductCategory);
        modelMapper.map(productDTO, productToUpdate);
        productToUpdate.setCategory(newProductCategory);
        return modelMapper.map(productDAO.save(productToUpdate), ProductDTO.class);
    }
}
