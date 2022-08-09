package com.neonq.inventory.controller;

import com.neonq.inventory.dto.PageableProductDTO;
import com.neonq.inventory.dto.ProductCategoryDTO;
import com.neonq.inventory.dto.ProductDTO;
import com.neonq.inventory.exception.ResourceNotFoundException;
import com.neonq.inventory.model.Product;
import com.neonq.inventory.service.ProductCategoryService;
import com.neonq.inventory.service.ProductService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/inventory/v1/")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ProductCategoryService productCategoryService;

    // Read
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @GetMapping("/products/search")
    public ResponseEntity<PageableProductDTO> getProducts(@RequestParam String keyword, @RequestParam int currentPage, @RequestParam int pageSize) {
        return new ResponseEntity<>(productService.getProductsBySearchKeyword(keyword, currentPage-1, pageSize), HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<PageableProductDTO> getProductsPerPage(@RequestParam int currentPage, @RequestParam int pageSize, @RequestParam(required= false) String sortBy) {
        return new ResponseEntity<>(productService.getProductsPerPage(currentPage-1, pageSize, sortBy), HttpStatus.OK);
    }

    // Create product
    @PostMapping("/product")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @NotNull ProductDTO product) throws ResourceNotFoundException {
        return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
    }

    // Update products
    @PutMapping("products/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) throws RuntimeException, ResourceNotFoundException {
        return new ResponseEntity<>(productService.updateProduct(id, productDTO), HttpStatus.OK);
    }

    //Delete products
    @DeleteMapping(value = "products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get all product categories
    @GetMapping("/categories")
    public ResponseEntity<List<ProductCategoryDTO>> getAllProductCategories() {
        return new ResponseEntity<>(productCategoryService.getAllCategories(), HttpStatus.OK);
    }

    // Create new product category
    @PostMapping("/category")
    public ResponseEntity<ProductCategoryDTO> createProductCategory(@RequestBody ProductCategoryDTO categoryDTO) {
        return new ResponseEntity<>(productCategoryService.createProductCategory(categoryDTO), HttpStatus.CREATED);
    }

}
