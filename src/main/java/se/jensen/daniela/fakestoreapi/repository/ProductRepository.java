package se.jensen.daniela.fakestoreapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jensen.daniela.fakestoreapi.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
