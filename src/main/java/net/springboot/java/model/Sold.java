package net.springboot.java.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Sold {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String fechaYHora;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private Set<ProductSold> productos;

    public Sold() {
        this.fechaYHora = Tools.obtenerFechaYHoraActual();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getTotal() {
        Float total = 0f;
        for (ProductSold productoVendido : this.productos) {
            total += productoVendido.getTotal();
        }
        return total;
    }

    public String getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(String fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    public Set<ProductSold> getProductos() {
        return productos;
    }

    public void setProductos(Set<ProductSold> productos) {
        this.productos = productos;
    }
}
