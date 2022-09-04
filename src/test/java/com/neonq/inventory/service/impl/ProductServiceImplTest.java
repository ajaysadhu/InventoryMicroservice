package com.neonq.inventory.service.impl;



import com.neonq.inventory.dao.ProductCategoryDAO;
import com.neonq.inventory.dao.ProductDAO;
import com.neonq.inventory.dto.ProductCategoryDTO;
import com.neonq.inventory.dto.ProductDTO;
import com.neonq.inventory.exception.ResourceNotFoundException;
import com.neonq.inventory.model.Product;
import com.neonq.inventory.model.ProductCategory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;


@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

    ProductCategory category;
    ProductCategoryDTO categoryDTO;
    Product productOne;
    @Before
    public void init(){
        MockitoAnnotations.openMocks(this);
        category = new ProductCategory(1L, "Books");
        categoryDTO = new ProductCategoryDTO();
        categoryDTO.setCategoryName("Books");
        categoryDTO.setId(1L);

        productOne = new Product().toBuilder().active(true).id(123L).category(category).name("My FirstBook").imageUrl("image").unitPrice(BigDecimal.valueOf(10.0)).unitsInStock(100).build();

    }


    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    ProductDAO productDao;

    @Mock
    ProductCategoryDAO productCategoryDAO;

    @Spy
    ModelMapper modelMapper;

    @Test
    public void getProductById_Success() {

       // ProductCategory category = new ProductCategory(1L, "Books");

        Optional<Product> optProduct = Optional.of(productOne);

        Mockito.when(productDao.findById(123L)).thenReturn(optProduct);

        // Actual Test code
        Assert.assertEquals(productService.getProductById(123L).getName(), "My FirstBook");
        Assert.assertEquals(productService.getProductById(123L).getCategory().getCategoryName(), "Books");

    }

    @Test(expected = IllegalArgumentException.class)
    public void getProduct_Exception() {
        productService.getProductById(null);
    }


    @Test
    public void createProductTest() {

        ProductDTO productDTO = new ProductDTO();
        productDTO.setActive(true);
        productDTO.setCategory(categoryDTO);
        productDTO.setName("Chess Box");

       Product newProduct = modelMapper.map(productDTO, Product.class);

        Mockito.when(productCategoryDAO.findById(1L)).thenReturn(Optional.of(category));

        Mockito.when(productDao.save(Mockito.any())).thenReturn(newProduct);

       Assert.assertEquals( productService.createProduct(productDTO).getName(), "Chess Box");
    }


    @Test
    public void updateProductTest() {

        ProductDTO productDTO = new ProductDTO();
        productDTO.setActive(true);
        productDTO.setCategory(categoryDTO);
        productDTO.setName("Chess Box");

        Product newProduct = modelMapper.map(productDTO, Product.class);


        Mockito.when(productDao.findById(123L)).thenReturn(Optional.of(productOne));
        Mockito.when(productCategoryDAO.findById(1L)).thenReturn(Optional.of(category));
        Mockito.when(productDao.save(Mockito.any())).thenReturn(newProduct);


        Assert.assertEquals( productService.updateProduct(123L, productDTO).getName(), "Chess Box");


    }

    @Test(expected = IllegalArgumentException.class)
    public void updateProduct_Exception() {
        productService.updateProduct(null, new ProductDTO());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateProduct_Exception1() {
        productService.updateProduct(4L, new ProductDTO());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateProduct_Exception2() {

        ProductDTO productDTO = modelMapper.map(productOne, ProductDTO.class);
        ProductCategoryDTO categoryDTO = new ProductCategoryDTO();
        categoryDTO.setId(2L);
        categoryDTO.setCategoryName("ABC");

        productService.updateProduct(1L, productDTO);
    }
}
