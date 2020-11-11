package net.springboot.java.repository;

import org.springframework.data.repository.CrudRepository;

import net.springboot.java.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer>{
	
	Employee findFirstEmployeeByCodigo(String codigo);
}
