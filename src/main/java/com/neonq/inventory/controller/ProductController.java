package com.neonq.inventory.controller;

import com.neonq.inventory.dto.*;
import com.neonq.inventory.exception.ResourceNotFoundException;
import com.neonq.inventory.model.Product;
import com.neonq.inventory.service.ProductCategoryService;
import com.neonq.inventory.service.ProductOrderHelper;
import com.neonq.inventory.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/inventory/v1/")
@Slf4j
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ProductCategoryService productCategoryService;

    @Autowired
    ProductOrderHelper productOrderHelper;

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }

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
        return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
    }

    // Update products
    @PutMapping("products/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) throws ResourceNotFoundException {
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
    /**
        For this method, Product Category DTO needs only categoryName as input, so it is made as NotEmpty in DTO and Validation id done here.
        304 - Content not modified message is sent to user if the category already exists.
        Resource Guide: https://zetcode.com/springboot/controlleradvice/#:~:text=%40ControllerAdvice%20is%20a%20specialization%20of,annotated%20with%20%40RequestMapping%20and%20similar.
     */
    @PostMapping("/category")
    public ResponseEntity<ProductCategoryDTO> createProductCategory(@RequestBody @Valid ProductCategoryDTO categoryDTO)  {
        return new ResponseEntity<>(productCategoryService.createProductCategory(categoryDTO), HttpStatus.CREATED);
    }


   /*
   - Client Retries can be upto the callers capacity.
    */
    @PostMapping("/product/placeorder")
    public ResponseEntity<OrderResponseDTO> orderProductsById(@RequestBody @Valid ProductOrderListDTO productOrders)  {
        OrderResponseDTO allOrders = new OrderResponseDTO();
        try {
              allOrders = productOrderHelper.orderProducts(productOrders);
        } catch(Exception ex) {
            log.error("Error while ordering Products: "+ ex.getMessage());
        }
        return new ResponseEntity<>(allOrders, HttpStatus.OK);
    }
}
