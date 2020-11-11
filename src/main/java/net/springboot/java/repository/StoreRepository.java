package net.springboot.java.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import net.springboot.java.model.Store;

@Service
public interface StoreRepository extends CrudRepository<Store, Integer> {

	Store findFirtStoreByCodigo(String codigo);
}
