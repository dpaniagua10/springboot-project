package net.springboot.java.web;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.springboot.java.repository.ProductRepository;
import net.springboot.java.repository.ProductMenuRepository;
import net.springboot.java.model.Menu;
import net.springboot.java.model.Product;
import net.springboot.java.model.ProductMenu;
import net.springboot.java.model.ProductToSell;
import net.springboot.java.repository.MenuRepository;

@Controller
@RequestMapping(path="/menus")
public class MenuController {
	
    @Autowired
    private ProductRepository productosRepository;//modelo de productos
    @Autowired
    private MenuRepository menuRepository;//modelo del menu -nombre-fecha-precio-detalle
    @Autowired
    private ProductMenuRepository productosMenuRepository;//modelo de ingredientes +
    
    @GetMapping(value = "/ver_menu")
	public String mostrarMenu(Model model) {
	    model.addAttribute("menus", menuRepository.findAll());
	    return "menus/ver_menu";
	    }//lista los menus existentes
    
    
    @PostMapping(value = "/agregar")//agregar ingrediente o se un producto
    public String agregarAlCarrito(@ModelAttribute Product producto, HttpServletRequest request, RedirectAttributes redirectAttrs) {
        ArrayList<ProductToSell> carrito = this.obtenerCarrito(request);
        Product productoBuscadoPorCodigo = productosRepository.findFirstByCodigo(producto.getCodigo());
        if (productoBuscadoPorCodigo == null) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "The product with the code " + producto.getCodigo() + " does not exist")
                    .addFlashAttribute("clase", "warning");
            return "redirect:/menus/agregar_menu";
        }
        if (productoBuscadoPorCodigo.sinExistencia()) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "the product is out of stock")
                    .addFlashAttribute("clase", "warning");
            return "redirect:/menus/agregar_menu";
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
        return "redirect:/menus/agregar_menu";
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
    
    @GetMapping(value = "/agregar_menu")//inicia metodo para registrar menus
    public String interfazVender(Model model, HttpServletRequest request) {
        model.addAttribute("producto", new Product());
        float total = 0;
        ArrayList<ProductToSell> carrito = this.obtenerCarrito(request);
        for (ProductToSell p: carrito) total += p.getTotal();
        model.addAttribute("total", total);
        model.addAttribute("menu", new Menu());//agrega menu sin datos
        return "menus/agregar_menu";
    }

    @PostMapping(value = "/quitar/{indice}")
    public String quitarDelCarrito(@PathVariable int indice, HttpServletRequest request) {
        ArrayList<ProductToSell> carrito = this.obtenerCarrito(request);
        if (carrito != null && carrito.size() > 0 && carrito.get(indice) != null) {
            carrito.remove(indice);
            this.guardarCarrito(carrito, request);
        }
        return "redirect:/menus/agregar_menu";
    }

    private void limpiarCarrito(HttpServletRequest request) {
        this.guardarCarrito(new ArrayList<>(), request);
    }

    @GetMapping(value = "/limpiar")
    public String cancelarVenta(HttpServletRequest request, RedirectAttributes redirectAttrs) {
        this.limpiarCarrito(request);
        redirectAttrs
                .addFlashAttribute("mensaje", "Menu cancelada")
                .addFlashAttribute("clase", "info");
        return "redirect:/menus/agregar_menu";
    }
    
    @PostMapping(value = "/terminar")//agregando menu
    public String terminarVenta(@ModelAttribute Menu menu, HttpServletRequest request, RedirectAttributes redirectAttrs, Model model) {
        ArrayList<ProductToSell> carrito = this.obtenerCarrito(request);
        
        if (carrito == null || carrito.size() <= 0) {
            return "redirect:/menus/agregar_menu";
        }
                
        Menu v = menuRepository.save(menu);//
        
        for (ProductToSell productoParaVender : carrito) {
           
            Product p = productosRepository.findById(productoParaVender.getId()).orElse(null);
            if (p == null) continue;
            //p.restarExistencia(productoParaVender.getCantidad());
            //productosRepository.save(p);
            ProductMenu productoDeMenu = new ProductMenu(productoParaVender.getCantidad(), productoParaVender.getNombre(), productoParaVender.getCodigo(), v);
 
            productosMenuRepository.save(productoDeMenu);
        }

        this.limpiarCarrito(request);

        redirectAttrs
                .addFlashAttribute("mensaje", "Menu Successful registered")
                .addFlashAttribute("clase", "success");
        return "redirect:/menus/agregar_menu";
    }
    
    @PostMapping(value = "/eliminar")
    public String eliminarMenu(@ModelAttribute Menu menu, RedirectAttributes redirectAttrs) {
        redirectAttrs
                .addFlashAttribute("mensaje", "Menu Successfully Removed")
                .addFlashAttribute("clase", "warning");
        menuRepository.deleteById(menu.getId());
        return "redirect:/menus/ver_menu";
    }

}