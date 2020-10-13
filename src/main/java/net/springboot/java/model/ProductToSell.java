package net.springboot.java.model;

public class ProductToSell extends Product {
    
	private Float cantidad;

    public ProductToSell(String nombre, String codigo, Float precio, Float existencia, Integer id, Float cantidad) {
        super(nombre, codigo, precio, existencia, id);
        this.cantidad = cantidad;
    }

    public ProductToSell(String nombre, String codigo, Float precio, Float existencia, Float cantidad) {
        super(nombre, codigo, precio, existencia);
        this.cantidad = cantidad;
    }

    public void aumentarCantidad() {
        this.cantidad++;
    }

    public Float getCantidad() {
        return cantidad;
    }

    public Float getTotal() {
        return this.getPrecio() * this.cantidad;
    }
}
