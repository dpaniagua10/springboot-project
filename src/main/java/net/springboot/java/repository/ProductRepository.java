package net.springboot.java.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import net.springboot.java.model.Product;

@Service
public interface ProductRepository extends CrudRepository<Product, Integer> {
	
	Product findFirstByCodigo(String codigo);
}
