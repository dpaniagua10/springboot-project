package net.springboot.java.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.springboot.java.repository.UserRepository;


@Controller
@RequestMapping(path = "/usuarios")
public class ListUsersController {

	
	@Autowired
	UserRepository usrRepository;
	
	@GetMapping(value = "/")
	public String mostrarUsuarios(Model model) {
	    model.addAttribute("usuarios", usrRepository.findAll());
	    return "usuarios/ver_usuarios";
	    }
}
