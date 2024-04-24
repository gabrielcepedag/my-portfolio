package com.example.massycake.controller;

import com.example.massycake.entities.*;
import com.example.massycake.payload.request.ItemCarritoRequest;
import com.example.massycake.service.EmpleadoService;
import com.example.massycake.service.FacturaService;
import com.example.massycake.service.PedidoService;
import com.example.massycake.service.ProductoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class PosController {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private EmpleadoService empleadoService;
    @Autowired
    private FacturaService facturaService;
    private List<Producto> listaProductos;
    private List<CarritoItem> listaCarrito;

    PosController(){
        listaCarrito = new ArrayList<>();
        listaProductos = new ArrayList<>();
    }

    @GetMapping("/pos")
    public ModelAndView mostrarPos(HttpServletRequest request) {
        Empleado empleadoLogueado = empleadoService.getEmpleadoLogueado();

        ModelAndView modelAndView = new ModelAndView("pos");
        listaProductos = productoService.buscarProductosCategoria("todos");

//        for (Producto producto : listaProductos) {
//            System.out.println(producto.getProductoId());
//            System.out.println(producto.getNombre());
//            System.out.println(producto.getCategoria());
//            System.out.println(producto.getPrecio());
//            System.out.println(producto.getFoto());
//        }
        HttpSession session = request.getSession();
        List<CarritoItem> carritoItems = new ArrayList<>();
        session.setAttribute(session.getId(), carritoItems);

        modelAndView.addObject("empleado", empleadoLogueado);
        modelAndView.addObject("productos", listaProductos);
        modelAndView.addObject("carritoItems", carritoItems);

        System.out.println("Voy a enviar a la vista el carrito con size: "+carritoItems.size());
        return modelAndView;
    }

    @PostMapping("/pos/carrito/enviar")
    public ResponseEntity<String> recibirDataCarrito(@RequestBody List<ItemCarritoRequest> itemsData, HttpServletRequest request) {
        List<CarritoItem> nuevaListaCarrito = new ArrayList<>(); // Crear una nueva lista temporal

        for (ItemCarritoRequest item : itemsData) {
            CarritoItem carritoItem = new CarritoItem(UUID.randomUUID(), getProductById(item.getProductoId()));
            System.out.println(carritoItem.getProducto().getNombre() + " | " + carritoItem.getProducto().getPrecio());

            carritoItem.setCantidad(item.getCantidad());
            nuevaListaCarrito.add(carritoItem);
        }

        HttpSession session = request.getSession();
        session.setAttribute(session.getId(), nuevaListaCarrito);
//        listaCarrito = nuevaListaCarrito; // Asignar la nueva lista a listaCarrito

        List<CarritoItem> lista = (List<CarritoItem>) session.getAttribute(session.getId());
        System.out.println(lista.get(0).getProducto().getNombre() + " | " + lista.get(0).getProducto().getPrecio());

//        System.out.println("\n\n\n");
//        for (CarritoItem carritoItem : lista) {
//            System.out.println(carritoItem.getProducto().getNombre() + "  |  " + carritoItem.getCantidad());
//        }
//        System.out.println("\n\n\n");

        return ResponseEntity.ok("Data del carrito recibida exitosamente");
    }

    @GetMapping("/pos/verificar-disponibilidad")
    public Map<String, Boolean> verificarDisponibilidad(@RequestParam Long id, @RequestParam int cantidad) {
        Map<String, Boolean> response = new HashMap<>();

        // Obtén el producto por su ID desde tu base de datos o sistema
        Producto producto = productoService.getProductoById(id);

        if (producto != null && producto.getCantidad() >= (cantidad + 1)) {
            response.put("disponible", true); // Hay suficiente cantidad disponible para agregar más
        } else {
            response.put("disponible", false); // No hay suficiente cantidad disponible
        }

        return response;
    }


    @GetMapping("/pos/carrito/vaciar")
    public ModelAndView vaciarCarrito(HttpServletRequest request){
        request.getSession().setAttribute(request.getSession().getId(), new ArrayList<CarritoItem>());
//        listaCarrito = new ArrayList<>();
        return new ModelAndView("redirect:/pos");
    }

    @PostMapping("/pos/facturar")
    public ResponseEntity<?> facturar(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession();
        List<CarritoItem> carritoItem = (List<CarritoItem>) session.getAttribute(session.getId());
        System.out.println("\n\nSIZE CARRITO: "+ carritoItem.size() + "\n\n");

        System.out.println("Facturando 1 ...");
        for (CarritoItem item : carritoItem) {
            System.out.println(item.getProducto().getNombre() + " | " + item.getProducto().getPrecio());
        }

        ResponseEntity<?> message = facturaService.facturar(carritoItem);

//        System.out.println("Facturando 2 ...");
//        for (CarritoItem item : carritoItem) {
//            System.out.println(item.getProducto().getNombre() + " | " + item.getProducto().getPrecio());
//        }
//        redirectAttributes.addFlashAttribute("message", message);
        model.addAttribute("message", message);
//        return "redirect:/pos/carrito/vaciar";
        return message;

    }

    @PostMapping("/pos/perfil/editar/{idEmpleado}")
    public String editarCajero(@Valid @ModelAttribute("empleado") Empleado empleado, @PathVariable("idEmpleado") long idEmpleado, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
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
            return "redirect:/pos";
        }
        return "redirect:/error";
    }

    private Producto getProductById(Long productId) {
        for (Producto producto : listaProductos) {
            if(Objects.equals(producto.getProductoId(), productId)) {
                System.out.println(producto.getNombre() + " | " + producto.getPrecio());

                return producto;
            }
        }
        return null;
    }

    private CarritoItem getCarritoItem(UUID carritoID) {
        for (CarritoItem carritoItem : listaCarrito) {
            if(carritoItem.getId().equals(carritoID)) {
                return carritoItem;
            }
        }
        return null;
    }
}
