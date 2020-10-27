package net.springboot.java.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.springboot.java.repository.SoldRepository;


@Controller
@RequestMapping(path = "/ventas")
public class SaleController {

	 @Autowired
	SoldRepository ventasRepository;
	
	@GetMapping(value = "/")
	public String mostrarVentas(Model model) {
	    model.addAttribute("ventas", ventasRepository.findAll());
	    return "ventas/ver_ventas";
	    }
}
