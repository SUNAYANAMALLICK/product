package com.om.application.product.controller;

import com.om.application.product.entity.Product;
import com.om.application.product.service.JobService;
import com.om.application.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private JobService jobService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateProduct() {
        Product product = new Product();
        when(productService.createProduct(product)).thenReturn(product);
        ResponseEntity<Product> response = productController.createProduct(product);
        assertEquals(product, response.getBody());
    }

    @Test
    void testGetProductById() {
        Long id = 1L;
        Product product = new Product();
        when(productService.getProductById(id)).thenReturn(product);
        ResponseEntity<Product> response = productController.getProductById(id);
        assertEquals(product, response.getBody());
    }

    // Test other controller methods similarly

    @Test
    void testUploadFile() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        String status = "success";
        when(jobService.processFileAsync(file)).thenReturn(status);
        ResponseEntity<String> response = productController.uploadFile(file);
        assertEquals("Job Running : Check status " + status, response.getBody());
    }
}
