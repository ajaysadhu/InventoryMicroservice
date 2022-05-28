package com.neonq.inventory.service;

import com.neonq.inventory.dto.ProductRequestDTO;
import com.neonq.inventory.utils.ProjectConstants;
import com.neonq.inventory.dao.ProductCategoryDAO;
import com.neonq.inventory.dao.ProductDAO;
import com.neonq.inventory.dto.ProductCategoryResponse;
import com.neonq.inventory.model.Product;
import com.neonq.inventory.model.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    ProductDAO productDAO;

    @Autowired
    ProductCategoryDAO productCategoryDAO;

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
        return productDAO.findByName(search, PageRequest.of(0, 6));

    }

    public Page<Product> getProductsByPageNum(int pageNum) {
        return productDAO.findAll(PageRequest.of(pageNum, ProjectConstants.PAGE_DISPLAY_COUNT));
    }

    public Page<Product> getProductsByCat(String category, int pageNum) {

        Page<Product> productSearchList = null;
        long prodCatId = 0;
        if (StringUtils.hasText(category)) {
            List<ProductCategory> productCategoryList = productCategoryDAO.findByCategoryName(category.toUpperCase());
            if (productCategoryList != null && productCategoryList.size() > 0) {
                ProductCategory productCategory = productCategoryList.get(0);
                prodCatId = productCategory.getId();
            }
            productSearchList = productDAO.findByCategory(prodCatId, PageRequest.of(pageNum, ProjectConstants.PAGE_DISPLAY_COUNT));
        }
        return productSearchList;
    }

    public ProductCategory createProdCatIfAbsent(String productCategory) {
        String prodCatUpper = productCategory.toUpperCase();
        List<ProductCategory> productCategoryList = productCategoryDAO.findByCategoryName(prodCatUpper);
        if (productCategoryList == null || productCategoryList.size() < 1) {
            ProductCategory newPrCat = new ProductCategory();
            newPrCat.setCategoryName(prodCatUpper);
            return productCategoryDAO.save(newPrCat);
        } else {
            return productCategoryList.get(0);
        }
    }

    public Page<Product> getProductsBySearchKeyword(String search, int pageNum) {
        Page<Product> productSearchList = null;

        if (StringUtils.hasText(search)) {
            productSearchList = productDAO.findByName(search, PageRequest.of(pageNum, ProjectConstants.PAGE_DISPLAY_COUNT));
        }

        return productSearchList;

    }

    public ProductCategoryResponse getAllCategories() {
        ProductCategoryResponse response = new ProductCategoryResponse();
        response.setCategories(productCategoryDAO.findAll().stream().map(a -> a.getCategoryName()).collect(Collectors.toList()));
        return response;
    }

    public Product createProduct(ProductRequestDTO product, ProductCategory prodCat) {
        Product productToDB = new Product();

        productToDB.setSku(product.getSku());
        productToDB.setName(product.getName());
        productToDB.setDescription(product.getDescription());
        productToDB.setUnitPrice(product.getUnitPrice());
        productToDB.setImageUrl(product.getImageUrl());
        productToDB.setActive(true);
        productToDB.setUnitsInStock(product.getUnitsInStock());
        productToDB.setCategory(prodCat);
        return productDAO.save(productToDB);
    }

    public Product updateProduct(Product productFromDB, ProductRequestDTO productDTO) {

        if (productFromDB.getCategory().getCategoryName() != productDTO.getCategory()) {
            ProductCategory prodCat = createProdCatIfAbsent(productDTO.getCategory().toUpperCase());
            productFromDB.setCategory(prodCat);
        }
        productFromDB.setSku(productDTO.getSku());
        productFromDB.setName(productDTO.getName());
        productFromDB.setDescription(productDTO.getDescription());
        productFromDB.setUnitPrice(productDTO.getUnitPrice());
        productFromDB.setImageUrl(productDTO.getImageUrl());
        productFromDB.setUnitsInStock(productDTO.getUnitsInStock());


        return productDAO.save(productFromDB);
    }
}
