package net.springboot.java.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Store {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	
	private String name;
	private String direction;
	private String codigo;
	
	public Store() {
		
	}
	
	public Store(String name, String direction, String codigo) {
		this.name=name;
		this.direction=direction;
		this.codigo=codigo;
	}
	
	public Store(String name, String direction, String codigo, int id) {
		this.name=name;
		this.direction=direction;
		this.codigo=codigo;
		this.id=id;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
}
