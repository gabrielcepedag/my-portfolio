package com.example.massycake.controller;

import com.example.massycake.entities.Empleado;
import com.example.massycake.entities.OrdenCompra;
import com.example.massycake.entities.constantes.EstadoOrdenCompra;
import com.example.massycake.payload.request.OrdenCompraRequest;
import com.example.massycake.service.EmpleadoService;
import com.example.massycake.service.OrdenCompraService;
import com.example.massycake.service.PedidoService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ordenesCompra")
public class OrdenCompraController {

    private OrdenCompraService ordenCompraService;
    private EmpleadoService empleadoService;

    public OrdenCompraController(OrdenCompraService ordenCompraService, EmpleadoService empleadoService) {
        this.ordenCompraService = ordenCompraService;
        this.empleadoService = empleadoService;
    }

    @GetMapping("")
    public String cocina(Model model) {
        Empleado empleadoLogueado = empleadoService.getEmpleadoLogueado();
        List<OrdenCompra> ordenesCompra = ordenCompraService.getAllOrdenesCompra();
        List<OrdenCompra> ordenesCompraProcesadas = ordenesCompra.stream()
                .filter(ordenCompra -> ordenCompra.getEstado().equals(EstadoOrdenCompra.PROCESADA))
                .toList();
        List<OrdenCompra> ordenesCompraGeneradas = ordenesCompra.stream()
                .filter(ordenCompra -> ordenCompra.getEstado().equalsIgnoreCase(EstadoOrdenCompra.SINPROCESAR))
                .toList();

        model.addAttribute("empleado", empleadoLogueado);
        model.addAttribute("ordenesCompra", ordenesCompra);
        model.addAttribute("ordenesCompraProcesadas", ordenesCompraProcesadas);
        model.addAttribute("ordenesCompraGeneradas", ordenesCompraGeneradas);
        for (OrdenCompra orden: ordenesCompraGeneradas
             ) {
            System.out.println(orden.getOrdenCompraId() + " - " + orden.getEstado() + "  -  " + orden.getFechaGenerada());;
        }
        return "ordenesCompra";
    }


    @PostMapping("/procesar")
    public ResponseEntity<?> guardarOrdenCompra(@Valid @RequestBody OrdenCompraRequest ordenCompraRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldError().getDefaultMessage());
        }
        try {
            OrdenCompra ordenCompra = ordenCompraService.getOrdenCompraById(ordenCompraRequest.getId());
            ordenCompra.setEstadoProcesada();
            ordenCompra.setCantidad(ordenCompraRequest.getCantidad());
            ordenCompra.setFechaProcesada(LocalDateTime.now());
            ordenCompraService.updateOrdenCompra(ordenCompra);
            return ResponseEntity.ok("Orden de compra procesada exitosamente");
        } catch (Exception e) {
            // En caso de que ocurra una excepción durante el proceso de guardado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la orden de compra");
        }
    }

    @PostMapping("/confirmarRecibido")
    public ResponseEntity<?> confirmarRecibido(@RequestParam("ordenCompraId") Long ordenCompraId) {
        try {
            OrdenCompra ordenCompra = ordenCompraService.getOrdenCompraById(ordenCompraId);
            if (ordenCompra == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Orden de compra no encontrada");
            }

            // Procesar la orden de compra y realizar las acciones necesarias
            ordenCompra.setEstadoRecibida();
            ordenCompra.setFechaRecibida(LocalDateTime.now());
            ordenCompraService.updateOrdenCompra(ordenCompra);

            return ResponseEntity.ok("Orden de compra marcada como recibida exitosamente");
        } catch (Exception e) {
            // En caso de que ocurra una excepción durante el proceso de confirmación
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al confirmar la orden de compra");
        }
    }
    @PostMapping("/eliminar/{idOrdenCompra}")
    public ResponseEntity<String> eliminarPedido(@PathVariable("idOrdenCompra") long idOrdenCompra) {
        ordenCompraService.deleteOrdenCompra(idOrdenCompra);

        return ResponseEntity.ok("Orden de Compra eliminada correctamente");
    }


}
