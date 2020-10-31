package net.springboot.java.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.springboot.java.model.Product;
import net.springboot.java.model.ProductSold;
import net.springboot.java.model.ProductToSell;
import net.springboot.java.model.Sold;
import net.springboot.java.repository.ProductRepository;
import net.springboot.java.repository.ProductSoldRepository;
import net.springboot.java.repository.SoldRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
@RequestMapping(path = "/vender")
public class SoldController {
    @Autowired
    private ProductRepository productosRepository;
    @Autowired
    private SoldRepository ventasRepository;
    @Autowired
    private ProductSoldRepository productosVendidosRepository;

    @PostMapping(value = "/quitar/{indice}")
    public String quitarDelCarrito(@PathVariable int indice, HttpServletRequest request) {
        ArrayList<ProductToSell> carrito = this.obtenerCarrito(request);
        if (carrito != null && carrito.size() > 0 && carrito.get(indice) != null) {
            carrito.remove(indice);
            this.guardarCarrito(carrito, request);
        }
        return "redirect:/vender/";
    }

    private void limpiarCarrito(HttpServletRequest request) {
        this.guardarCarrito(new ArrayList<>(), request);
    }

    @GetMapping(value = "/limpiar")
    public String cancelarVenta(HttpServletRequest request, RedirectAttributes redirectAttrs) {
        this.limpiarCarrito(request);
        redirectAttrs
                .addFlashAttribute("mensaje", "Venta cancelada")
                .addFlashAttribute("clase", "info");
        return "redirect:/vender/";
    }

    @PostMapping(value = "/terminar")
    public String terminarVenta(HttpServletRequest request, RedirectAttributes redirectAttrs) {
        ArrayList<ProductToSell> carrito = this.obtenerCarrito(request);
        
        if (carrito == null || carrito.size() <= 0) {
            return "redirect:/vender/";
        }
        Sold v = ventasRepository.save(new Sold());
      
        for (ProductToSell productoParaVender : carrito) {
           
            Product p = productosRepository.findById(productoParaVender.getId()).orElse(null);
            if (p == null) continue;
            p.restarExistencia(productoParaVender.getCantidad());
            productosRepository.save(p);
            ProductSold productoVendido = new ProductSold(productoParaVender.getCantidad(), productoParaVender.getPrecio(), productoParaVender.getNombre(), productoParaVender.getCodigo(), v);
 
            productosVendidosRepository.save(productoVendido);
        }

        this.limpiarCarrito(request);

        redirectAttrs
                .addFlashAttribute("mensaje", "Sale Successful")
                .addFlashAttribute("clase", "success");
        return "redirect:/vender/";
    }

    @GetMapping(value = "/")
    public String interfazVender(Model model, HttpServletRequest request) {
        model.addAttribute("producto", new Product());
        float total = 0;
        ArrayList<ProductToSell> carrito = this.obtenerCarrito(request);
        for (ProductToSell p: carrito) total += p.getTotal();
        model.addAttribute("total", total);
        return "vender/vender";
    }

    
	private ArrayList<ProductToSell> obtenerCarrito(HttpServletRequest request) {
        @SuppressWarnings("unchecked")
		ArrayList<ProductToSell> carrito = (ArrayList<ProductToSell>) request.getSession().getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
        }
        return carrito;
    }

    private void guardarCarrito(ArrayList<ProductToSell> carrito, HttpServletRequest request) {
        request.getSession().setAttribute("carrito", carrito);
    }

    @PostMapping(value = "/agregar")
    public String agregarAlCarrito(@ModelAttribute Product producto, HttpServletRequest request, RedirectAttributes redirectAttrs) {
        ArrayList<ProductToSell> carrito = this.obtenerCarrito(request);
        Product productoBuscadoPorCodigo = productosRepository.findFirstByCodigo(producto.getCodigo());
        if (productoBuscadoPorCodigo == null) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "The product with the code " + producto.getCodigo() + " does not exist")
                    .addFlashAttribute("clase", "warning");
            return "redirect:/vender/";
        }
        if (productoBuscadoPorCodigo.sinExistencia()) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "the product is out of stock")
                    .addFlashAttribute("clase", "warning");
            return "redirect:/vender/";
        }
        boolean encontrado = false;
        for (ProductToSell productoParaVenderActual : carrito) {
            if (productoParaVenderActual.getCodigo().equals(productoBuscadoPorCodigo.getCodigo())) {
                productoParaVenderActual.aumentarCantidad();
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            carrito.add(new ProductToSell(productoBuscadoPorCodigo.getNombre(), productoBuscadoPorCodigo.getCodigo(), productoBuscadoPorCodigo.getPrecio(), productoBuscadoPorCodigo.getExistencia(), productoBuscadoPorCodigo.getId(), 1f));
        }
        this.guardarCarrito(carrito, request);
        return "redirect:/vender/";
    }
}
