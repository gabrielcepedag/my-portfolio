package com.example.massycake.controller;

import com.example.massycake.entities.Cliente;
import com.example.massycake.entities.Empleado;
import com.example.massycake.entities.Pedido;
import com.example.massycake.entities.Producto;
import com.example.massycake.payload.request.IngredienteRequest;
import com.example.massycake.payload.request.PedidoRequest;
import com.example.massycake.service.EmpleadoService;
import com.example.massycake.service.ProductoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/inventario")
public class InventarioController {

    private ModelMapper modelMapper = new ModelMapper();
    private final ProductoService productoService;
    private final EmpleadoService empleadoService;

    public InventarioController(ProductoService productoService, EmpleadoService empleadoService) {
        this.productoService = productoService;
        this.empleadoService = empleadoService;
    }

    @GetMapping("")
    public String listarIngredientes(Model model){
        Empleado empleadoLogueado = empleadoService.getEmpleadoLogueado();
        List<Producto> ingredientes = productoService.getAllIngredientes();
        int stock = 0, dispMin = 0, agotados = 0;
        for (Producto p1: ingredientes) {
            if (p1.getCantidad() > p1.getDispMin()) {
                stock++;
            } else if (p1.getCantidad() == 0) {
                agotados++;
            } else if (p1.getCantidad() <= p1.getDispMin()) {
                dispMin++;
            }
        }

        model.addAttribute("empleado", empleadoLogueado);
        model.addAttribute("stock", stock);
        model.addAttribute("agotados", agotados);
        model.addAttribute("dispMin", dispMin);
        model.addAttribute("ingredientes", ingredientes);
        model.addAttribute("cantProductos", ingredientes.size());
        model.addAttribute("cantIngredientes", ingredientes.size());

        return "inventario";
    }

    /*** PRODUCTO (INGREDIENTE) ***/
    // Create Requests
    @GetMapping("/ingrediente/crear")
    public String regProducto(Model model){
        Empleado empleadoLogueado = empleadoService.getEmpleadoLogueado();
        List<String> unidades = productoService.getAllUnidades();

        model.addAttribute("empleado", empleadoLogueado);
        model.addAttribute("unidades", unidades);

        return "regIngrediente";
    }
    @PostMapping("/ingrediente/crear")
    public String crearIngrediente(@Valid @ModelAttribute("Producto") Producto ingrediente, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            String fieldName;
            String errorMessage;

            if (fieldError != null) {
                fieldName = fieldError.getField();
                errorMessage = fieldError.getDefaultMessage();
                model.addAttribute("campo", fieldName);
                model.addAttribute("mensaje", errorMessage);
                model.addAttribute("typeError", true);
                return "regIngrediente";

            }else{
                return "redirect:/400";
            }
        }
        Producto prod = productoService.createIngrediente(ingrediente);

        return "redirect:/inventario";
    }

    // Edit Requests
    @GetMapping("/ingrediente/editar/{idIngrediente}")
    public String editProducto(@PathVariable("idIngrediente") long idIngrediente, Model model) {
        Empleado empleadoLogueado = empleadoService.getEmpleadoLogueado();
        Producto ingrediente = productoService.getProductoById(idIngrediente);
        List<String> unidades = productoService.getAllUnidades();
//        unidades.remove(ingrediente.getUnidadMedida());

        Collections.swap(unidades, 0, unidades.indexOf(ingrediente.getUnidadMedida()));
        model.addAttribute("unidades", unidades);
        model.addAttribute("empleado", empleadoLogueado);
        model.addAttribute("ingrediente", ingrediente);
        model.addAttribute("editarProducto", true);

        return "regIngrediente";
    }
    @PostMapping("/ingrediente/editar/{idIngrediente}")
    public String editarProducto(@Valid @ModelAttribute("producto") Producto producto, @PathVariable long idIngrediente, BindingResult bindingResult, Model model) {

        producto.setProductoId(idIngrediente);
        productoService.editIngrediente(producto);
        return "redirect:/inventario";
    }

    // Delete Requests
    @PostMapping("/ingrediente/eliminar/{idIngrediente}")
    public ResponseEntity<String> eliminarIngrediente(@PathVariable Long idIngrediente) {
        if (productoService.deleteIngrediente(idIngrediente) == null) {
            System.out.println("ERROR: No puedes eliminar este producto porque no es un ingrediente o porque no existe");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/ingrediente/addstock/{idIngrediente}")
    public ResponseEntity<String> editarProducto(@PathVariable long idIngrediente, @RequestParam("cantidad") int cantidad) {
        Producto ingrediente = productoService.getProductoById(idIngrediente);

        ingrediente.setProductoId(idIngrediente);
        ingrediente.setCantidad(ingrediente.getCantidad() + cantidad);

        productoService.editIngrediente(ingrediente);

        String message = "Stock added successfully.";
        return ResponseEntity.ok(message);
    }

    //************************    MANEJADOR DE ERRORES DINAMICO    *************************//
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request, Model model) {
        String requestPath = request.getRequestURI();

        // PARA MANEJAR ERRORES EN "/ingrediente/editar/{idIngrediente}"
        if (requestPath.matches(".*/ingrediente/editar/\\d+") && request.getMethod().equals("POST")) {
            BindingResult bindingResult = ex.getBindingResult();
            Long idIngrediente = extractIdFromPath(requestPath);

            if (bindingResult.hasErrors()) {
                FieldError fieldError = bindingResult.getFieldError();
                String fieldName;
                String errorMessage;

                if (fieldError != null) {
                    Producto ingrediente = productoService.getProductoById(idIngrediente);
                    fieldName = fieldError.getField();
                    errorMessage = fieldError.getDefaultMessage();
                    model.addAttribute("campo", fieldName);
                    model.addAttribute("mensaje", errorMessage);
                    model.addAttribute("typeError", true);
                    model.addAttribute("ingrediente", ingrediente);
                    model.addAttribute("editarProducto", true);
                    return "regIngrediente";
                }
            }
        }
        // end of "/ingrediente/editar/{idIngrediente}"

        // PARA MANEJAR ERRORES EN "/..."

        // end of "/..."


        // PARA MANEJAR ERRORES GENERALES (DEFAULT)
        return "redirect:/400";
    }

    private Long extractIdFromPath(String path) {
        String[] pathParts = path.split("/");
        return Long.parseLong(pathParts[pathParts.length - 1]);
    }


}
