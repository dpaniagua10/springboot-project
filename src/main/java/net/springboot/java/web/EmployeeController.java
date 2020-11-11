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

import net.springboot.java.model.Employee;
import net.springboot.java.repository.EmployeeRepository;

@Controller
@RequestMapping(value = "/empleados")
public class EmployeeController {

	@Autowired
    private EmployeeRepository employeeRepository;

	@PostMapping(value = "/agregar")
    public String guardarEmpleado(@ModelAttribute @Valid Employee empleado, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            return "empleados/agregar_empleado";
        }
        if (employeeRepository.findFirstEmployeeByCodigo(empleado.getCodigo()) != null) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "\r\n"
                    		+ "There is already a Employee with that Code")
                    .addFlashAttribute("clase", "warning");
            return "redirect:/empleados/agregar";
        }
        employeeRepository.save(empleado);
        redirectAttrs
                .addFlashAttribute("mensaje", "Employee Added Correctly")
                .addFlashAttribute("clase", "success");
        return "redirect:/empleados/agregar";
    }
	
	@PostMapping(value = "/eliminar")
    public String eliminarEmpleado(@ModelAttribute Employee empleado, RedirectAttributes redirectAttrs) {
        redirectAttrs
                .addFlashAttribute("mensaje", "Employee Successfully Removed")
                .addFlashAttribute("clase", "warning");
        employeeRepository.deleteById(empleado.getId());
        return "redirect:/empleados/mostrar";
    }
	
	@GetMapping(value = "/agregar")
    public String agregarempleado(Model model) {
        model.addAttribute("empleado", new Employee());
        return "empleados/agregar_empleado";
    }
    
    @GetMapping(value = "/mostrar")
    public String mostrarEmpleados(Model model) {
        model.addAttribute("empleados", employeeRepository.findAll());
        return "empleados/ver_empleados";
    }
    
    @GetMapping(value = "/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable int id, Model model) {
        model.addAttribute("empleado", employeeRepository.findById(id).orElse(null));
        return "empleados/editar_empleado";
    }
    
    @PostMapping(value = "/editar/{id}")
    public String actualizarempleado(@ModelAttribute @Valid Employee empleado, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            if (empleado.getId() != null) {
                return "empleados/editar_empleado";
            }
            return "redirect:/empleados/mostrar";
        }
        Employee posibleempleadoExistente = employeeRepository.findFirstEmployeeByCodigo(empleado.getCodigo());

        if (posibleempleadoExistente != null && !posibleempleadoExistente.getId().equals(empleado.getId())) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "There is already a Employee with that Code")
                    .addFlashAttribute("clase", "warning");
            return "redirect:/empleados/agregar";
        }
        employeeRepository.save(empleado);
        redirectAttrs
                .addFlashAttribute("mensaje", "Employee Edited Successfully")
                .addFlashAttribute("clase", "success");
        return "redirect:/empleados/mostrar";
    }
    
}
