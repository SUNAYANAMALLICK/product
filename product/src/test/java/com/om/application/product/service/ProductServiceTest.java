package com.om.application.product.service;

import com.om.application.product.entity.Product;
import com.om.application.product.exception.userdefined.ResourceNotFoundException;
import com.om.application.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProduct_success() {
        Product product = new Product();
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product createdProduct = productService.createProduct(product);

        assertNotNull(createdProduct);
        assertEquals(product, createdProduct);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testGetProductById_success() {
        Product product = new Product();
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductById(1L);

        assertNotNull(foundProduct);
        assertEquals(product, foundProduct);
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetProductById_notFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.getProductById(1L);
        });

        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateProduct_success() {
        Product existingProduct = new Product();
        existingProduct.setId(1L);
        Product updatedProductDetails = new Product();
        updatedProductDetails.setName("Updated Name");

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        Product updatedProduct = productService.updateProduct(1L, updatedProductDetails);

        assertNotNull(updatedProduct);
        assertEquals("Updated Name", updatedProduct.getName());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    public void testListProducts_success() {
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(productRepository.findAll()).thenReturn(products);

        List<Product> foundProducts = productService.listProducts(null, null);

        assertNotNull(foundProducts);
        assertEquals(2, foundProducts.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testListProducts_withSearch() {
        List<Product> products = Arrays.asList(new Product());
        when(productRepository.findByNameContaining(any(String.class))).thenReturn(products);

        List<Product> foundProducts = productService.listProducts(null, "search");

        assertNotNull(foundProducts);
        assertEquals(1, foundProducts.size());
        verify(productRepository, times(1)).findByNameContaining("search");
    }

    @Test
    public void testProcessFile_withValidFile() throws IOException {
        InputStream inputStream = mock(InputStream.class);
        when(multipartFile.getInputStream()).thenReturn(inputStream);

        // Assuming valid CSV data is provided in the input stream
        List<Product> products = List.of(new Product(), new Product());
        when(productRepository.saveAll(anyList())).thenReturn(products);

        String result = productService.processFile(multipartFile);

        assertEquals("Success", result);
        verify(productRepository, times(1)).saveAll(anyList());
    }

    @Test
    public void testProcessFile_withInvalidFile() throws IOException {
        InputStream inputStream = mock(InputStream.class);
        when(multipartFile.getInputStream()).thenThrow(new IOException("Invalid file"));

        Exception exception = assertThrows(IOException.class, () -> {
            productService.processFile(multipartFile);
        });

        assertEquals("Invalid file", exception.getMessage());
    }

    @Test
    public void testParseFile_success() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "id,name,description\n1,Product1,Description1".getBytes());
        List<Product> products = productService.parseFile(file);

        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals("Product1", products.get(0).getName());
    }

    @Test
    public void testParseFile_withIOException() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getInputStream()).thenThrow(new IOException("Error reading file"));

        assertThrows(IOException.class, () -> {
            productService.parseFile(file);
        });
    }
}
