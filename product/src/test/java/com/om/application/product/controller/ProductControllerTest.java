package com.om.application.product.controller;

import com.om.application.product.entity.Product;
import com.om.application.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductControllerTest {

    @Mock
    private ProductService productService;


    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    public ProductControllerTest() {
        MockitoAnnotations.openMocks(this);
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

    @Test
    public void testCreateProduct_withNullProduct() {
        when(productService.createProduct(null)).thenThrow(new IllegalArgumentException("Product cannot be null"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productController.createProduct(null);
        });

        assertEquals("Product cannot be null", exception.getMessage());
    }

    @Test
    public void testGetProductById_withNonExistingId() {
        when(productService.getProductById(anyLong())).thenReturn(null);

        ResponseEntity<Product> response = productController.getProductById(999L);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    public void testUpdateProduct_withNonExistingId() {
        when(productService.updateProduct(anyLong(), any(Product.class))).thenThrow(new IllegalArgumentException("Product not found"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productController.updateProduct(999L, new Product());
        });

        assertEquals("Product not found", exception.getMessage());
    }



    @Test
    public void testListProducts_withNoResults() {
        when(productService.listProducts(any(String.class), any(String.class)))
                .thenReturn(Collections.emptyList());

        ResponseEntity<List<Product>> response = productController.listProducts("non-existent-category", "non-existent-search");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(0, response.getBody().size());
    }

    @Test
    public void testUploadFile_withEmptyFile() throws IOException {
        MultipartFile emptyFile = new MockMultipartFile("file", new byte[0]);

        when(productService.processFile(any(MultipartFile.class))).thenThrow(new IllegalArgumentException("File is empty"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productController.uploadFile(emptyFile);
        });

        assertEquals("File is empty", exception.getMessage());
    }

    @Test
    public void testUploadFile_withInvalidFileFormat() throws IOException {
        MockMultipartFile invalidFile = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Invalid content".getBytes());

        when(productService.processFile(any(MultipartFile.class))).thenThrow(new IllegalArgumentException("Invalid file format"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productController.uploadFile(invalidFile);
        });

        assertEquals("Invalid file format", exception.getMessage());
    }

}
