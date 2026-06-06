package se.jensen.daniela.fakestoreapi.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.jensen.daniela.fakestoreapi.model.Product;
import se.jensen.daniela.fakestoreapi.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {


    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }


    @GetMapping("/fetch")
    public List<Product> fetchProducts() {
        return service.fetchAndSaveProducts();
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }

    @GetMapping
    public List<Product> getAll() {
        return service.getAllProducts();
    }

}