package com.neonq.inventory.controller;

import com.neonq.inventory.dto.PageableProductDTO;
import com.neonq.inventory.dto.ProductCategoryDTO;
import com.neonq.inventory.dto.ProductDTO;
import com.neonq.inventory.exception.ResourceExistsWarning;
import com.neonq.inventory.exception.ResourceNotFoundException;
import com.neonq.inventory.model.Product;
import com.neonq.inventory.service.ProductCategoryService;
import com.neonq.inventory.service.ProductService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;
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
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @NotNull @Valid ProductDTO product) throws ResourceNotFoundException {
        System.out.println(LocalDateTime.now());
        return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
    }

    // Update products
    @PutMapping("products/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) throws RuntimeException, ResourceNotFoundException {
        return new ResponseEntity<>(productService.updateProduct(id, productDTO), HttpStatus.OK);
    }

    //Delete products
    @DeleteMapping(value = "products/{id}")
    public ResponseEntity<Long> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get all product categories
    @GetMapping("/categories")
    public ResponseEntity<List<ProductCategoryDTO>> getAllProductCategories() {
        return new ResponseEntity<>(productCategoryService.getAllCategories(), HttpStatus.OK);
    }

    // Create new product category
    /**
        For this method, Product Category DTO needs only categoryName as input, so it is made as NotEmpty in DTO and Validation id done here.
        304 - Content not modified message is sent to user if the category already exists.
        Resource Guide: https://zetcode.com/springboot/controlleradvice/#:~:text=%40ControllerAdvice%20is%20a%20specialization%20of,annotated%20with%20%40RequestMapping%20and%20similar.
     */
    @PostMapping("/category")
    public ResponseEntity<ProductCategoryDTO> createProductCategory(@RequestBody @Valid ProductCategoryDTO categoryDTO)  {
        return new ResponseEntity<>(productCategoryService.createProductCategory(categoryDTO), HttpStatus.CREATED);
    }

    // Multiple instances
    //If 2 users are requiring chess product of quantity 4.
    // Spring @Transactional and JPA Locks (Optimistic Locks/ Pessimistic Locks).
    // Read lock, write_lock
    // findBySku(String skuName)
    // updateQuantityForId()

    // Spring Retry for specific exception like Lock exception.
    // Retry the same call to deduct quantity for the product like 4 times. Delay option for every retry.
    // Circuit Breaker is design pattern which is implemented with Spring Retry.
    @PostMapping("/product/order")
    public ResponseEntity<ProductDTO> orderProductBySkuName(@RequestParam String skuName,@RequestParam int quantity )  {
        System.out.println(LocalDateTime.now());
        System.out.println("test");
        return new  ResponseEntity<>(productService.orderProduct(skuName,quantity),HttpStatus.OK);
    }
    @Recover
    private ResponseEntity<String>  orderProductBySkuNameRecover(RuntimeException e){
        e.printStackTrace();
        //System.out.println("recover" + quantity+ e.getMessage());
        return new ResponseEntity<String>( "Product not availabe..", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
