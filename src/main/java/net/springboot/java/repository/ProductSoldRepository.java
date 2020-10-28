package net.springboot.java.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import net.springboot.java.model.ProductSold;

@Service
public interface ProductSoldRepository extends CrudRepository<ProductSold, Integer> {

}
