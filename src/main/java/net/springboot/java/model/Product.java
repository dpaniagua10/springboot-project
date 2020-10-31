package net.springboot.java.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "You must specify the name")
    @Size(min = 1, max = 50, message = "The name must be between 1 and 50")
    private String nombre;

    @NotNull(message = "You must specify the barcode")
    @Size(min = 1, max = 50, message = "The code must measure between 1 and 50")
    private String codigo;

    @NotNull(message = "You must specify the price")
    @Min(value = 0, message = "El precio mínimo es 0")
    private Float precio;

    @NotNull(message = "You must specify the existence")
    @Min(value = 0, message = "Minimum stock is 0")
    private Float existencia;


    public Product(String nombre, String codigo, Float precio, Float existencia, Integer id) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.precio = precio;
        this.existencia = existencia;
        this.id = id;
    }

    public Product(String nombre, String codigo, Float precio, Float existencia) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.precio = precio;
        this.existencia = existencia;
    }

    public Product(@NotNull(message = "You must specify the name") @Size(min = 1, max = 50, message = "The code must measure between 1 and 50") String codigo) {
        this.codigo = codigo;
    }

    public Product() {
    }

    public boolean sinExistencia() {
        return this.existencia <= 0;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public Float getExistencia() {
        return existencia;
    }

    public void setExistencia(Float existencia) {
        this.existencia = existencia;
    }

    public void restarExistencia(Float existencia) {
        this.existencia -= existencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
