package net.springboot.java.repository;

import org.springframework.data.repository.CrudRepository;

import net.springboot.java.model.Sold;

public interface SoldRepository extends CrudRepository<Sold, Integer> {

}
