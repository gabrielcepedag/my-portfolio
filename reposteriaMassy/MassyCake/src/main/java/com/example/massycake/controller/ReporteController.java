package com.example.massycake.controller;

import com.example.massycake.entities.*;
import com.example.massycake.entities.constantes.EstadoPedidoConst;
import com.example.massycake.entities.constantes.TipoPedidoConst;
import com.example.massycake.payload.request.LoginRequest;
import com.example.massycake.payload.request.PruebaRequest;
import com.example.massycake.service.EmpleadoService;
import com.example.massycake.service.FacturaService;
import com.example.massycake.service.PedidoService;
import com.example.massycake.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Month;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reportes")
public class ReporteController {
    private EmpleadoService empleadoService;
    private final ProductoService productoService;
    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private FacturaService facturaService;

    public ReporteController(ProductoService productoService, EmpleadoService empleadoService) {
        this.productoService = productoService;
        this.empleadoService = empleadoService;
    }

    @GetMapping("/clientes")
    public String reporteClientes(Model model) {
        Empleado empleadoLogueado = empleadoService.getEmpleadoLogueado();

        // Obtener la lista de clientes
        List<Cliente> clientes = pedidoService.getClientes();

        // Obtener la cantidad de clientes registrados el último mes
        List<Cliente> clientesUltimoMes = getClientesUltimoMes(clientes);
        List<Cliente> clientesUltimoagno = getClientesUltimoAgno(clientes);

        // Agregar los resultados al modelo para que estén disponibles en la vista
        model.addAttribute("empleado", empleadoLogueado);
        model.addAttribute("clientes", clientes);
        model.addAttribute("clientesUltimoMes", clientesUltimoMes);
        model.addAttribute("clientesUltimoAgno", clientesUltimoagno);

        return "repoClientes";
    }

    // Método para obtener la lista de clientes del último mes
    public static List<Cliente> getClientesUltimoMes(List<Cliente> clientes) {
        List<Cliente> clientesUltimoMes = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int mesActual = calendar.get(Calendar.MONTH);
        int anoActual = calendar.get(Calendar.YEAR);

        for (Cliente cliente : clientes) {
            Calendar fechaCliente = Calendar.getInstance();
            fechaCliente.setTime(cliente.getFechaCreacion());

            int mesCreacion = fechaCliente.get(Calendar.MONTH);
            int anoCreacion = fechaCliente.get(Calendar.YEAR);

            if (mesCreacion == mesActual && anoCreacion == anoActual) {
                clientesUltimoMes.add(cliente);
            }
        }

        return clientesUltimoMes;
    }

    // Método para obtener la lista de clientes del último año
    public static List<Cliente> getClientesUltimoAgno(List<Cliente> clientes) {
        List<Cliente> clientesUltimoAno = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int anoActual = calendar.get(Calendar.YEAR);

        for (Cliente cliente : clientes) {
            Calendar fechaCliente = Calendar.getInstance();
            fechaCliente.setTime(cliente.getFechaCreacion());

            int anoCreacion = fechaCliente.get(Calendar.YEAR);

            if (anoCreacion == anoActual) {
                clientesUltimoAno.add(cliente);
            }
        }

        return clientesUltimoAno;
    }

    @GetMapping("/ventas")
    public String reporteVentas(Model model) {
        Empleado empleadoLogueado = empleadoService.getEmpleadoLogueado();

        // Obtener la lista de facturas
        List<Factura> facturas = facturaService.getFacturas();
        List<Pedido> pedidosPendientes = pedidoService.getAllPedidos().stream().filter(pedido -> pedido.getEstado().equals(EstadoPedidoConst.REGISTRADO)).toList();
        List<DetalleFactura> detalleFacturas = facturaService.getDetallesFacturas();
        // Calcular los valores requeridos
        float ingresosHoy = facturaService.getIngresosFacturasHoy();
        float ingresosMes = facturaService.getIngresoMesActual();
        float ingresosAno = calcularIngresosAno(facturas);
        int ventasTotales = facturas.size(); // Número total de facturas

        // Supongo que tienes métodos para calcular las ventas por tipo (cabina y encargo)
        int ventasCabina = calcularVentasPorTipo(facturas, TipoPedidoConst.CABINA);
        int ventasEncargo = calcularVentasPorTipo(facturas, TipoPedidoConst.PERSONALIZADO);

        // Mapa de costo total por mes para todos los clientes
        Map<String, Double> costoTotalPorMes = facturas.stream()
                .collect(Collectors.groupingBy(
                        factura -> factura.getFecha().getMonth().toString(),
                        Collectors.summingDouble(Factura::getTotal)
                ));

        // Mapa de costo total por mes para un cliente específico
        Map<String, Double> costoTotalPorMesPersonalizado = facturas.stream()
                .filter(factura -> {
                    String cedula = factura.getCliente().getCedula();
                    return !cedula.equalsIgnoreCase("000-0000000-0") && !cedula.equalsIgnoreCase("111-1111111-1");
                })
                .collect(Collectors.groupingBy(
                        factura -> factura.getFecha().getMonth().toString(),
                        Collectors.summingDouble(Factura::getTotal)
                ));

        // Mapa de costo total por mes para clientes que no sean el cliente específico
        Map<String, Double> costoTotalPorMesCabina = facturas.stream()
                .filter(factura -> {
                    String cedula = factura.getCliente().getCedula();
                    return cedula.equalsIgnoreCase("000-0000000-0") || cedula.equalsIgnoreCase("111-1111111-1");
                })
                .collect(Collectors.groupingBy(
                        factura -> factura.getFecha().getMonth().toString(),
                        Collectors.summingDouble(Factura::getTotal)
                ));

        System.out.println(costoTotalPorMesCabina.keySet());
        // Lista de nombres de meses
        List<String> nombresMeses = Arrays.stream(Month.values())
                .map(month -> month.toString().toUpperCase())
                .collect(Collectors.toList());

        System.out.println(nombresMeses);

        Map<String, Double> costoAnual = new LinkedHashMap<>();
        Map<String, Double> costoAnualPersonalizado = new LinkedHashMap<>();
        Map<String, Double> costoAnualCabina = new LinkedHashMap<>();

        for (String mes : nombresMeses) {
            Double costoTotalMes = costoTotalPorMes.getOrDefault(mes, 0.0);
            Double costoTotalMesPersonalizado = costoTotalPorMesPersonalizado.getOrDefault(mes, 0.0);
            Double costoTotalMesCabina = costoTotalPorMesCabina.getOrDefault(mes, 0.0);

            costoAnual.put(mes, costoTotalMes);
            costoAnualPersonalizado.put(mes, costoTotalMesPersonalizado);
            costoAnualCabina.put(mes, costoTotalMesCabina);
        }


        System.out.println(costoAnual);
        System.out.println(costoAnualPersonalizado);
        System.out.println(costoAnualCabina);


        // Agregar los valores al modelo
        model.addAttribute("empleado", empleadoLogueado);
        model.addAttribute("ingresosHoy", ingresosHoy);
        model.addAttribute("ingresosMes", ingresosMes);
        model.addAttribute("ingresosAno", ingresosAno);
        model.addAttribute("ventasTotales", ventasTotales);
        model.addAttribute("ventasCabina", ventasCabina);
        model.addAttribute("ventasEncargo", ventasEncargo);
        model.addAttribute("pedidosPendientes", pedidosPendientes);
        model.addAttribute("pedidosCabina", pedidosPendientes);
        model.addAttribute("pedidosEncargo", pedidosPendientes);
        model.addAttribute("detalles", detalleFacturas);
        model.addAttribute("facturas", facturas);
        model.addAttribute("costoTotalPorMes", costoAnual);
        model.addAttribute("costoTotalPorMesPersonalizado", costoAnualPersonalizado);
        model.addAttribute("costoTotalPorMesCabina", costoAnualCabina);

        return "repoVentas";
    }

    // Métodos para calcular los ingresos y ventas por tipo
    private float calcularIngresosAno(List<Factura> facturas) {
        float ingresosAno = 0;
        Calendar calendarioHoy = Calendar.getInstance();
        Calendar calendarioFactura = Calendar.getInstance();

        for (Factura factura : facturas) {
            Date fechaFactura = Date.from(factura.getFecha().atZone(ZoneId.systemDefault()).toInstant());
            calendarioFactura.setTime(fechaFactura);

            if (calendarioFactura.get(Calendar.YEAR) == calendarioHoy.get(Calendar.YEAR)) {
                ingresosAno += factura.getTotal();
            }
        }

        return ingresosAno;
    }

    private int calcularVentasPorTipo(List<Factura> facturas, String tipo) {
        int ventasPorTipo = 0;

        for (Factura factura : facturas) {
            List<Producto> productos = factura.getProductos();

            for (Producto producto : productos) {
                // Supongamos que los productos tienen un atributo "tipo" que indica su tipo
                if (producto.getTipo().equals(tipo)) {
                    ventasPorTipo++;
                }
            }
        }

        return ventasPorTipo;
    }


    @GetMapping("/inventario")
    public String reporteInventario(Model model) {
        Empleado empleadoLogueado = empleadoService.getEmpleadoLogueado();
//        List<Producto> productos = productoService.getAllProductos().stream().filter(producto -> !producto.getTipo().equals("Personalizado")).toList();
//        List<Producto> productosIngredientes = productoService.getAllProductos().stream().filter(producto -> producto.getTipo().equals("Ingrediente")).toList();
//        List<Producto> productosCabina = productoService.getAllProductos().stream().filter(producto -> producto.getTipo().equals("Cabina")).toList();

        List<Producto> productosIngredientes = productoService.getAllIngredientes();
        List<Producto> productosCabina = productoService.getAllCabina();
        List<Producto> productos = new ArrayList<>(productosCabina);
        productos.addAll(productosIngredientes);
        model.addAttribute("productos", productos);
        model.addAttribute("productosIngredientes", productosIngredientes);
        model.addAttribute("productosCabina", productosCabina);

        long cantProductosDispMin = productos.stream()
                .filter(producto -> producto.getCantidad() <= producto.getDispMin() && producto.getCantidad() > 0)
                .count();

        long cantProductosFueraStock = productos.stream()
                .filter(producto -> producto.getCantidad() <= 0)
                .count();

        long cantProductosInStock = productos.stream()
                .filter(producto -> producto.getCantidad() > producto.getDispMin())
                .count();

        model.addAttribute("cantProductosDispMin", cantProductosDispMin);
        model.addAttribute("cantProductosFueraStock", cantProductosFueraStock);
        model.addAttribute("cantProductosInStock", cantProductosInStock);

        model.addAttribute("empleado", empleadoLogueado);

        return "repoInventario";
    }

}