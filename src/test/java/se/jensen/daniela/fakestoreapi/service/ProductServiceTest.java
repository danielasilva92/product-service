package se.jensen.daniela.fakestoreapi.service;

import org.junit.jupiter.api.Test;
import se.jensen.daniela.fakestoreapi.model.Product;
import se.jensen.daniela.fakestoreapi.repository.ProductRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Test
    void getAllProducts() {

        // arrange
        ProductRepository repository = mock(ProductRepository.class);
        ProductService productService = new ProductService(repository);

        Product product1 = new Product();
        product1.setTitle("test product");

        Product product2 = new Product();
        product2.setTitle("test product 2");

        List<Product> fakeProducts = List.of(product1, product2);

        when(repository.count()).thenReturn(2L);
        when(repository.findAll()).thenReturn(fakeProducts);

        //Ract
        List<Product> result = productService.getAllProducts();

        //assert
        assertEquals(2, result.size());
        assertEquals("test product", result.get(0).getTitle());
        assertEquals("test product 2", result.get(1).getTitle());

        verify(repository).count();
        verify(repository).findAll();
    }
}