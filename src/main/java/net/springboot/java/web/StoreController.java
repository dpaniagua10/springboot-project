package net.springboot.java.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.springboot.java.model.Store;
import net.springboot.java.repository.StoreRepository;

@Controller
@RequestMapping(value = "/tiendas")
public class StoreController {
	
	@Autowired
	private StoreRepository storeRepository;
	
	@GetMapping(value = "/agregar")
    public String agregarTinda(Model model) {
        model.addAttribute("tienda", new Store());
        return "tiendas/agregar_tienda";
    }
	
	@GetMapping(value = "/mostrar")
    public String mostrarTiendas(Model model) {
        model.addAttribute("tiendas", storeRepository.findAll());
        return "tiendas/ver_tiendas";
    }
	
	@PostMapping(value = "/agregar")
    public String guardarTienda(@ModelAttribute @Valid Store tienda, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            return "tiendas/agregar_tienda";
        }
        if (storeRepository.findFirtStoreByCodigo(tienda.getCodigo()) != null) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "\r\n"
                    		+ "There is already a Store with that Code")
                    .addFlashAttribute("clase", "warning");
            return "redirect:/tiendas/agregar";
        }
        storeRepository.save(tienda);
        redirectAttrs
                .addFlashAttribute("mensaje", "Store Added Correctly")
                .addFlashAttribute("clase", "success");
        return "redirect:/tiendas/agregar";
    }
	
    @PostMapping(value = "/eliminar")
    public String eliminarTienda(@ModelAttribute Store tienda, RedirectAttributes redirectAttrs) {
        redirectAttrs
                .addFlashAttribute("mensaje", "Store Successfully Removed")
                .addFlashAttribute("clase", "warning");
        storeRepository.deleteById(tienda.getId());
        return "redirect:/tiendas/mostrar";
    }
    
    @GetMapping(value = "/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable int id, Model model) {
        model.addAttribute("tienda", storeRepository.findById(id).orElse(null));
        return "tiendas/editar_tienda";
    }
    
    @PostMapping(value = "/editar/{id}")
    public String actualizartienda(@ModelAttribute @Valid Store tienda, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            if (tienda.getId() != null) {
                return "tiendas/editar_tienda";
            }
            return "redirect:/tiendas/mostrar";
        }
        Store posibletiendaExistente = storeRepository.findFirtStoreByCodigo(tienda.getCodigo());

        if (posibletiendaExistente != null && !posibletiendaExistente.getId().equals(tienda.getId())) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "There is already a Store with that Code")
                    .addFlashAttribute("clase", "warning");
            return "redirect:/tiendas/agregar";
        }
        storeRepository.save(tienda);
        redirectAttrs
                .addFlashAttribute("mensaje", "Store Edited Successfully")
                .addFlashAttribute("clase", "success");
        return "redirect:/tiendas/mostrar";
    }

}
