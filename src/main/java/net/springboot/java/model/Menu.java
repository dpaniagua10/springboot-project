package net.springboot.java.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Menu {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	private String codigo;
	private String name;
	private String description;
	private Float price;
	
	@OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
	private Set<ProductMenu> productos;

	public Menu() {
		
	}
	
	public Menu(Integer id, String codigo, String name, String description, Float price, Set<ProductMenu> productos) {
		this.id = id;
		this.codigo=codigo;
		this.name = name;
		this.description = description;
		this.price = price;
		this.productos = productos;
	}
	
	public Menu(int id) {
		this.id=id;
	}
	public Menu(String codigo, String name, String description, Float price, Set<ProductMenu> productos) {
		this.codigo=codigo;
		this.name = name;
		this.description = description;
		this.price = price;
		this.productos = productos;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Set<ProductMenu> getProductos() {
		return productos;
	}

	public void setProductos(Set<ProductMenu> productos) {
		this.productos = productos;
	}
	
	
}
