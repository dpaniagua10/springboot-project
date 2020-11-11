package net.springboot.java.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ProductMenu {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Float cantidad;
    private String nombre, codigo;
    
    @ManyToOne
    @JoinColumn
    private Menu menu;

    public ProductMenu(){
    	
    }
    
	public ProductMenu(Integer id, Float cantidad, String nombre, String codigo, Menu menu) {
		this.id = id;
		this.cantidad = cantidad;
		this.nombre = nombre;
		this.codigo = codigo;
		this.menu = menu;
	}
	
	public ProductMenu(Float cantidad, String nombre, String codigo, Menu menu) {
		this.cantidad = cantidad;
		this.nombre = nombre;
		this.codigo = codigo;
		this.menu = menu;
	}
	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Float getCantidad() {
		return cantidad;
	}

	public void setCantidad(Float cantidad) {
		this.cantidad = cantidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
    
    
}

