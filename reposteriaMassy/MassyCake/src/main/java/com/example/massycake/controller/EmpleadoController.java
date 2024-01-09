package com.example.massycake.controller;

import com.example.massycake.entities.Empleado;
import com.example.massycake.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {
    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping("")
    public String listarEmpleados(Model model){
        Empleado empleadoLogueado = empleadoService.getEmpleadoLogueado();
        List<EmpleadoService.EmpleadoResponse> response = empleadoService.getAllEmpleados(10,0);
        int cantEmpleados = empleadoService.getCantEmpleados();

        model.addAttribute("empleado", empleadoLogueado);
        model.addAttribute("empleados", response);
        model.addAttribute("cantEmpleados", cantEmpleados);
        model.addAttribute("cantReposteros", empleadoService.getCantReposteros());
        model.addAttribute("cantCajeros", empleadoService.getCantCajeros());

        return "empleados";
    }

    @GetMapping("/{idEmpleado}")
    public String findOneEmpleado(Model model, @PathVariable Long idEmpleado){
        Empleado empleadoLogueado = empleadoService.getEmpleadoLogueado();
        EmpleadoService.EmpleadoResponse emp = empleadoService.getOneEmpleadoResponse(idEmpleado);

        model.addAttribute("empleado", empleadoLogueado);
        model.addAttribute("empleado", emp);

        return "empleados";
    }

    // Create Requests
    @GetMapping("/crear")
    public String regEmpleado(Model model) {
        Empleado empleadoLogueado = empleadoService.getEmpleadoLogueado();
        List<String> roles = empleadoService.getAllRoles();

        model.addAttribute("empleadoLog", empleadoLogueado);
        model.addAttribute("roles", roles);

        return "regUsuario";
    }
    @PostMapping("/crear")
    public String crearEmpleado(@Valid @ModelAttribute("empleado") Empleado empleado, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            System.out.println("Ocurrio un error en el controlador empleado-crearEmpleado: " + bindingResult.toString());
            return "redirect:/400";
        }

        // CreateEmpleado CODES => -1 = Error en Cedula | -2 = Error en Username | 1 = Operación Exitosa
        int createResult = empleadoService.createEmpleado(empleado);

        if (createResult == 1) {
            redirectAttributes.addFlashAttribute("creacionExitosa", true);
            return "redirect:/empleados";
        } else if (createResult == -1) {
            System.out.println("CEDULA YA EXISTE");
            redirectAttributes.addFlashAttribute("empleado", empleado);
            redirectAttributes.addFlashAttribute("cedulaError", true);
        } else {
            System.out.println("NOMBRE DE USUARIO YA EXISTE");
            redirectAttributes.addFlashAttribute("empleado", empleado);
            redirectAttributes.addFlashAttribute("usernameError", true);
        }
        return "redirect:/empleados/crear";
    }

    // Edit Requests
    @GetMapping("/editar/{idEmpleado}")
    public String editEmpleado(@PathVariable("idEmpleado") long idEmpleado, Model model) {
        Empleado empleadoLogueado = empleadoService.getEmpleadoLogueado();
        EmpleadoService.EmpleadoResponse emp = empleadoService.getOneEmpleadoResponse(idEmpleado);
        List<String> roles = empleadoService.getAllRoles();
        roles.remove(emp.rol());

        model.addAttribute("empleadoLog", empleadoLogueado);
        model.addAttribute("empleado", emp);
        model.addAttribute("editarUsuario", true);
        model.addAttribute("roles", roles);

        return "regUsuario";
    }
    @PostMapping("/editar/{idEmpleado}")
    public String editarEmpleado(@Valid @ModelAttribute("empleado") Empleado empleado, @PathVariable("idEmpleado") long idEmpleado, RedirectAttributes redirectAttributes, BindingResult bindingResult) {        if (bindingResult.hasErrors()) {
            //En este caso un 400 BAD REQUEST porque los campos no estan completos.
            System.out.println("Ocurrio un error en el controlador empleado-editarEmpleado: "+bindingResult.toString());
            return "regUsuario";
        }
        Empleado empleadoLogueado = empleadoService.getEmpleadoLogueado();
        Empleado auxEmpleado = empleadoService.getOneById(idEmpleado);
        empleado.setEmpleadoId(idEmpleado);

        // Manteniendo la misma contraseña si no se manda nada en el form
        if(empleado.getPassword().isEmpty()){
            empleado.setPassword(auxEmpleado.getPassword());
        }

        int editResult = empleadoService.editarEmpleado(empleado);
        if (editResult == 1) {
            redirectAttributes.addFlashAttribute("edicionExitosa", true);
            return "redirect:/empleados";
        }

        return "redirect:/empleados/editar";
    }

    // Delete Requests
    @PostMapping("/eliminar/{idEmpleado}")
    public ResponseEntity<String> eliminarEmpleado(@PathVariable Long idEmpleado) {
        try {
            empleadoService.deleteEmpleado(idEmpleado);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    // Information Requests
    @GetMapping("/perfil/{idEmpleado}")
    public String miPerfil(@PathVariable("idEmpleado") long idEmpleado, Model model) {
        Empleado empleadoLogueado = empleadoService.getEmpleadoLogueado();
        EmpleadoService.EmpleadoResponse emp = empleadoService.getOneEmpleadoResponse(idEmpleado);

        model.addAttribute("empleadoLog", empleadoLogueado);
        model.addAttribute("empleado", emp);
        model.addAttribute("info", true);
        return "miPerfil";
    }
}
