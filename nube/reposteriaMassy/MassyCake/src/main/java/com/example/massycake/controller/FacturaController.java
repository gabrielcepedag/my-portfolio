package com.example.massycake.controller;

import com.example.massycake.entities.DetalleFactura;
import com.example.massycake.entities.Empleado;
import com.example.massycake.entities.Factura;
import com.example.massycake.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/facturas")
public class FacturaController {
    private final EmpleadoService empleadoService;
    private final FacturaService facturaService;

    public FacturaController(EmpleadoService empleadoService,FacturaService facturaService) {
        this.empleadoService = empleadoService;
        this.facturaService = facturaService;
    }

    @GetMapping("")
    public String facturas(Model model){
        Empleado empleadoLogueado = empleadoService.getEmpleadoLogueado();
        List<Factura> facturas = facturaService.getFacturas();
        List<Factura> facturasACredito = facturaService.getFacturasACredito(facturas);
        List<Factura> facturasPendientes = facturaService.getFacturasPendientes(facturas);

        model.addAttribute("empleado", empleadoLogueado);
        model.addAttribute("facturas", facturas);
        model.addAttribute("facturasACredito", facturasACredito);
        model.addAttribute("facturasPendientes", facturasPendientes);

        return "facturas";
    }

    @GetMapping("/detalle/{idFactura}")
    public String verDetalle(@PathVariable("idFactura") long idFactura, Model model){
        Empleado empleadoLogueado = empleadoService.getEmpleadoLogueado();
        Factura factura = facturaService.getFacturaById(idFactura);
        List<DetalleFactura> detallesFactura = factura.getDetalleFacturas();

        model.addAttribute("empleado", empleadoLogueado);
        model.addAttribute("factura", factura);
        model.addAttribute("detallesFactura", detallesFactura);

        return "verDetalleFactura";
    }
}
