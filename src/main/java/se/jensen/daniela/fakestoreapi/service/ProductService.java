package se.jensen.daniela.fakestoreapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import se.jensen.daniela.fakestoreapi.model.Product;
import se.jensen.daniela.fakestoreapi.repository.ProductRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final RestTemplate restTemplate;

    @Value("${fakestore.base-url}")
    private String fakeStoreBaseUrl1;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
        this.restTemplate = new RestTemplate();
    }

    public List<Product> fetchAndSaveProducts() {
        String url = fakeStoreBaseUrl1;

        Product[] response = restTemplate.getForObject(url, Product[].class);
        List<Product> products = Arrays.asList(response);
        repository.saveAll(products);
        return repository.findAll();
    }

    public List<Product> getAllProducts() {
        if (repository.count() == 0) {
            fetchAndSaveProducts();
        }

        return repository.findAll();
    }
}
