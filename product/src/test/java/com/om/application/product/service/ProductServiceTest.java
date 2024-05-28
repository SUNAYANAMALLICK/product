package com.om.application.product.service;

import com.om.application.product.entity.Product;
import com.om.application.product.exception.userdefined.ResourceNotFoundException;
import com.om.application.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateProduct() {
        Product product = new Product();
        when(productRepository.save(any(Product.class))).thenReturn(product);
        Product savedProduct = productService.createProduct(product);
        assertEquals(product, savedProduct);
    }

    @Test
    void testGetProductById() {
        Long id = 1L;
        Product product = new Product();
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        Product retrievedProduct = productService.getProductById(id);
        assertEquals(product, retrievedProduct);
    }

    @Test
    void testGetProductById_NotFound() {
        Long id = 1L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(id));
    }

    @Test
    void testUpdateProduct() {
        Long id = 1L;
        Product existingProduct = new Product();
        Product updatedProduct = new Product();
        when(productRepository.findById(id)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);
        Product result = productService.updateProduct(id, updatedProduct);
        assertEquals(updatedProduct, result);
    }

    // Test other methods similarly

//    @Test
//    void testParseFile() throws IOException {
//        // Load test CSV file from resources
//        ClassPathResource resource = new ClassPathResource("product.csv");
//        InputStream inputStream = resource.getInputStream();
//
//        // Create MultipartFile object with test file content
//        MultipartFile file = new MockMultipartFile("product.csv", inputStream);
//
//        // Mock the behavior of productRepository.saveAll to return a list of products
//        List<Product> productList = Collections.singletonList(new Product());
//        when(productRepository.saveAll(anyIterable())).thenReturn(productList);
//
//        // Call the parseFile method with the test MultipartFile
//        List<Product> result = productService.parseFile(file);
//
//        // Assert that the result matches the expected list of products
//        assertEquals(productList, result);
//    }
}
