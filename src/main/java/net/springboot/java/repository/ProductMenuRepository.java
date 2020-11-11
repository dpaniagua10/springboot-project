package net.springboot.java.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import net.springboot.java.model.ProductMenu;

@Service
public interface ProductMenuRepository extends CrudRepository<ProductMenu, Integer> {

}
