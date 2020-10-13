package net.springboot.java.repository;

import org.springframework.data.repository.CrudRepository;

import net.springboot.java.model.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {
	
	Product findFirstByCodigo(String codigo);
}
