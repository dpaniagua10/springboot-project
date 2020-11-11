package net.springboot.java.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private String nombre;
	private String apellido;
	private char genero;
	private String email;
	private Float salario;
	private String codigo;
	
	public Employee() {
		
	}
	
	public Employee(String nombre, String apellido, char genero, String email, Float salario, String codigo ) {
		this.nombre=nombre;
		this.apellido=apellido;
		this.genero=genero;
		this.email=email;
		this.salario=salario;
		this.codigo=codigo;
	}
	
	public Employee(String nombre, String apellido, char genero, String email, Float salario, String codigo, int id) {
		this.nombre=nombre;
		this.apellido=apellido;
		this.genero=genero;
		this.email=email;
		this.salario=salario;
		this.codigo=codigo;
		this.id=id;
	}
	
	
	public char getGenero() {
		return genero;
	}

	public void setGenero(char genero) {
		this.genero = genero;
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
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Float getSalario() {
		return salario;
	}
	public void setSalario(Float salario) {
		this.salario = salario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
