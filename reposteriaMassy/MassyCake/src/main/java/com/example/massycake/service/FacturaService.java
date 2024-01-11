package com.example.massycake.service;

import com.example.massycake.entities.*;
import com.example.massycake.repository.DetalleFacturaRepo;
import com.example.massycake.repository.EmpleadoRepo;
import com.example.massycake.repository.FacturaRepo;
import com.example.massycake.utils.ERole;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class FacturaService {
    @Autowired
    private EmpleadoService empleadoService;
    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private FacturaRepo facturaRepo;
    @Autowired
    private DetalleFacturaRepo detalleFacturaRepo;
    @Autowired
    private ProductoService productoService;

    public FacturaService() {
    }

    @Transactional
    public ResponseEntity<?> facturar(List<CarritoItem> listaCarrito){
        Factura factura = new Factura();

        try{
            factura.setCliente(pedidoService.getClienteCabina());
            factura.setEmpleado(empleadoService.getEmpleadoLogueado());
            factura.setDescuento((float) 0);
            factura.setFecha(LocalDateTime.now());
            factura.setEsACredito(false);

            for(CarritoItem carritoItem: listaCarrito) {
                DetalleFactura detalleFactura = new DetalleFactura();
                detalleFactura.setFactura(factura);
                detalleFactura.setDescuento((float) 0);
                detalleFactura.setCantidad(carritoItem.getCantidad());

                Producto producto = carritoItem.getProducto();
                // Verificar si hay suficiente cantidad disponible
                if (producto.getCantidad() < carritoItem.getCantidad()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No hay suficiente cantidad disponible para el producto: " + producto.getNombre());
                }

                detalleFactura.setProducto(carritoItem.getProducto());
                detalleFactura.setSubtotal((float) (carritoItem.getCantidad() * carritoItem.getProducto().getPrecio()));
                detalleFactura.setPrecioUnitario(carritoItem.getProducto().getPrecio());
//                factura.getProductos().add(carritoItem.getProducto());

                // Reducir la cantidad del producto en el carrito
                producto.setCantidad(producto.getCantidad() - carritoItem.getCantidad());
                productoService.actualizarProducto(producto); // Actualizar en la base de datos

                System.out.println("Cantidad reducida");
                factura.addDetalle(detalleFactura);
            }

            float total = calcularTotal(listaCarrito);
            factura.setTotal(total);
            guardarFactura(factura);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body("Factura procesada exitosamente!");

    }

    private float calcularTotal(List<CarritoItem> listaCarrito) {
        float total = 0;
        for (CarritoItem item : listaCarrito) {
            total += (item.getProducto().getPrecio() * item.getCantidad());
        }
        return total;
    }

    public void guardarFactura(Factura factura) {
        facturaRepo.save(factura);
    }

    public List<Factura> getFacturas() {
        return facturaRepo.findAll();
    }

    public List<DetalleFactura> getDetallesFacturas() {
        return detalleFacturaRepo.findAll();
    }

    public List<Factura> getFacturasACredito(List<Factura> facturas) {
        List<Factura> facturasACredito = new ArrayList<>();
        for (Factura factura: facturas) {
            if(factura.isEsACredito()){
                facturasACredito.add(factura);
            }
        }
        return facturasACredito;
    }

    public List<Factura> getFacturasPendientes(List<Factura> facturas) {
        List<Factura> facturasPendientes = new ArrayList<>();

        for (Factura factura : facturas) {
            double totalAbonado = 0.0;
            for (ReciboPago reciboPago : factura.getReciboPagos()) {
                totalAbonado += reciboPago.getAbonado();
            }

            if (totalAbonado < factura.getTotal()) {
                facturasPendientes.add(factura);
            }
        }

        return facturasPendientes;
    }

    public Float getIngresosFacturasHoy() {
        Float ingresosHoy = 0.0F;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String now = dtf.format(LocalDateTime.now());

        ingresosHoy = facturaRepo.getIngresosFacturasHoy(now);
        return ingresosHoy != null ? ingresosHoy : 0.0F;
    }

    public int getVentasCantByTipo(String tipo) {
        return facturaRepo.countByTipo(tipo);
    }

    /*

    Facturas: 20:09:53 - 20:10:33
    Pedidos Pendientes: 20:10:33 - 20:10:33
    Detalles facturas: 20:10:33 - 20:10:33
    Ingresos Hoy: 20:10:33
    Ingresos Mes: 20:10:33
    Calcular Ventas por tipo Cabina: 20:10:33 - 20:10:51

     */



    public Float getIngresoMesActual() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String now = dtf.format(LocalDateTime.now());

        Float ingresosMes = facturaRepo.getIngresosByMes(now);

        return ingresosMes != null ? ingresosMes : 0.0F;
    }

    public Float getIngresoAnoActual() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String now = dtf.format(LocalDateTime.now());

        Float ingresosAno = facturaRepo.getIngresosByAno(now);
        return  ingresosAno != null ? ingresosAno : 0.0F;
    }

    public Integer getCantVentasHoy() {
        Integer cant = 0;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String now = dtf.format(LocalDateTime.now());

        cant = facturaRepo.getCantVentas(now);
        return cant;
    }

    public Factura getFacturaById(long idFactura) {
        return facturaRepo.findById(idFactura).orElse(null);
    }

}