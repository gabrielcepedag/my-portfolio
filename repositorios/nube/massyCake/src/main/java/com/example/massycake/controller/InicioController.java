package com.example.massycake.controller;

import com.example.massycake.entities.*;
import com.example.massycake.entities.constantes.EstadoPedidoConst;
import com.example.massycake.entities.constantes.TipoPedidoConst;
import com.example.massycake.payload.request.LoginRequest;
import com.example.massycake.service.EmpleadoService;
import com.example.massycake.service.FacturaService;
import com.example.massycake.service.PedidoService;
import com.example.massycake.service.ProductoService;
import com.example.massycake.utils.ERole;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class InicioController {
    private final EmpleadoService empleadoService;
    private final PedidoService pedidoService;
    private final ProductoService productoService;
    private final FacturaService facturaService;


    public InicioController(EmpleadoService empleadoService, PedidoService pedidoService, ProductoService productoService, FacturaService facturaService) {
        this.empleadoService = empleadoService;
        this.pedidoService = pedidoService;
        this.productoService = productoService;
        this.facturaService = facturaService;
    }

    @GetMapping("/" )
    public String inicio(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            Empleado empleadoLogueado = empleadoService.getOneByUsername(userDetails.getUsername()).orElse(null);

            for (GrantedAuthority rol: auth.getAuthorities()) {
                if(rol.getAuthority().equalsIgnoreCase("ROLE_"+ERole.ADMIN.name())){
                    return "redirect:/home";
                }
                else if(rol.getAuthority().equalsIgnoreCase("ROLE_"+ERole.REPOSTERO.name())){
                    return "redirect:/cocina";
                }
                else if(rol.getAuthority().equalsIgnoreCase("ROLE_"+ERole.CAJERO.name())){
                    return "redirect:/pos";
                }
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String home(Model model){
        int cantPendientes = 0;
        int cantLibras = 0;

        Empleado empleadoLogueado = empleadoService.getEmpleadoLogueado();
        List<Pedido> pedidosHoy = pedidoService.getPedidosPendientesByDay(1);
        Float ingresosHoy = facturaService.getIngresosFacturasHoy();
        Float ingresosMes= facturaService.getIngresoMesActual();
        Integer cantVentasHoy = facturaService.getCantVentasHoy();
        List<Producto> productos = productoService.getAllProductos().stream().filter(producto -> !producto.getTipo().equals("Personalizado")).toList();

        for (Pedido p1: pedidosHoy) {
            if (p1.getEstado().equalsIgnoreCase(EstadoPedidoConst.REGISTRADO)){
                cantPendientes++;
            }
            for (DetallePedido d1: p1.getDetallePedido()) {
                if (d1.getUnidad().equalsIgnoreCase("Lb")){
                    cantLibras += d1.getPeso();
                }
            }
        }

        Map<String, Long> productCountByCategory = productos.stream()
                .collect(Collectors.groupingBy(
                        producto -> {
                            if (producto.getCantidad() <= 0) {
                                return "FueraStock";
                            } else if (producto.getDispMin() != null && producto.getCantidad() <= producto.getDispMin()) {
                                return "CantProductosDispMin";
                            } else {
                                return "CantProductosInStock";
                            }
                        },
                        Collectors.counting()
                ));

        long cantProductosDispMin = productCountByCategory.getOrDefault("CantProductosDispMin", 0L);
        long cantProductosFueraStock = productCountByCategory.getOrDefault("FueraStock", 0L);
        long cantProductosInStock = productCountByCategory.getOrDefault("CantProductosInStock", 0L);

        if(empleadoLogueado != null){
            model.addAttribute("cantVentasHoy", cantVentasHoy);
            model.addAttribute("ingresosMes", ingresosMes);
            model.addAttribute("ingresosHoy", ingresosHoy);
            model.addAttribute("empleado", empleadoLogueado);
            model.addAttribute("cantPendientes", cantPendientes);
            model.addAttribute("totalPedidos", pedidosHoy.size());
            model.addAttribute("cantLibrasHoy", cantLibras);
            model.addAttribute("cantProductosDispMin", cantProductosDispMin);
            model.addAttribute("cantProductosFueraStock", cantProductosFueraStock);
            model.addAttribute("cantProductosInStock", cantProductosInStock);
            return "home";
        }
        return "redirect/error";
    }

    @PostMapping("/perfil/editar/{idEmpleado}")
    public String editarEmpleado(@Valid @ModelAttribute("empleado") Empleado empleado, @PathVariable("idEmpleado") long idEmpleado, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //En este caso un 400 BAD REQUEST porque los campos no estan completos.
            System.out.println("Ocurrio un error en el controlador perfil-editarEmpleado: "+bindingResult.toString());
            return "redirect:/error";
        }
        Empleado auxEmpleado = empleadoService.getOneById(idEmpleado);
        empleado.setEmpleadoId(idEmpleado);

        // Manteniendo la misma contraseña si no se manda nada en el form
        if(empleado.getPassword().isEmpty()){
            empleado.setPassword(auxEmpleado.getPassword());
        }

        int editResult = empleadoService.editarEmpleado(empleado);
        if (editResult == 1) {
            redirectAttributes.addFlashAttribute("edicionExitosa", true);
            return "redirect:/perfil";
        }
        return "redirect:/error";
    }

    // MAPEOS TEMPORALES
    @GetMapping("/login")
    public String login(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            return "redirect:/";
        }
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @GetMapping("/productos")
    public String productos(Model model){
        Empleado empleadoLogueado = empleadoService.getEmpleadoLogueado();
        List<Producto> productos = productoService.getAllProductosNotIngrediente();
        List<Producto> productosCabina = productoService.getAllCabina();
        List<Producto> productosPersonalizados = productoService.getAllPersonalizados();

        int stock = 0, dispMin = 0, agotados = 0;
        for (Producto p1: productos) {
            if (p1.getTipo().equalsIgnoreCase(TipoPedidoConst.CABINA)){
                if (p1.getCantidad() > p1.getDispMin()){
                    stock++;
                }
                else if (p1.getCantidad() == 0){
                    agotados++;
                }
                else if (p1.getCantidad() <= p1.getDispMin()){
                    dispMin++;
                }
            }
        }
        model.addAttribute("empleado", empleadoLogueado);
        model.addAttribute("stock", stock);
        model.addAttribute("agotados", agotados);
        model.addAttribute("dispMin", dispMin);
        model.addAttribute("cantProductos", productos.size());
        model.addAttribute("productos", productos);
        model.addAttribute("productosCabina", productosCabina);
        model.addAttribute("productosPersonalizados", productosPersonalizados);

        return "productos";
    }
    @PostMapping("/productos/addstock/{idIngrediente}")
    public ResponseEntity<String> addStockCabina(@PathVariable long idIngrediente, @RequestParam("cantidad") int cantidad) {
        Producto producto = productoService.getProductoById(idIngrediente);

        producto.setProductoId(idIngrediente);
        producto.setCantidad(producto.getCantidad() + cantidad);

        productoService.editIngrediente(producto);

        String message = "Stock added successfully.";
        return ResponseEntity.ok(message);
    }
    @GetMapping("/ventas")
    public String ventas(Model model){
        Empleado empleadoLogueado = empleadoService.getEmpleadoLogueado();
        // Obtener la lista de facturas
        System.out.println("FACTURAS");
        List<Factura> facturas = facturaService.getFacturas();
        System.out.println("PEDIOS PENDIENTES");
        List<Pedido> pedidosPendientes = pedidoService.getAllPedidos().stream().filter(pedido -> pedido.getEstado().equals(EstadoPedidoConst.REGISTRADO)).toList();
        System.out.println("DETALLES FACTURAS");
        List<DetalleFactura> detalleFacturas = facturaService.getDetallesFacturas();
        // Calcular los valores requeridos
        System.out.println("INGRESOS HOY");
        float ingresosHoy = facturaService.getIngresosFacturasHoy();
        System.out.println("INGRESOS MES");
        float ingresosMes = facturaService.getIngresoMesActual();
        System.out.println("INGRESOS ANO");
        float ingresosAno = facturaService.getIngresoAnoActual();
        int ventasTotales = facturas.size(); // Número total de facturas

        // Supongo que tienes métodos para calcular las ventas por tipo (cabina y encargo)
        System.out.println("CALCULAR VENTAS POR TIPO CABINA");
        int ventasCabina = facturaService.getVentasCantByTipo(TipoPedidoConst.CABINA);
        System.out.println("CALCULAR VENTAS POR TIPO PERSONALIZADO");
        int ventasEncargo = facturaService.getVentasCantByTipo(TipoPedidoConst.PERSONALIZADO);

        // Mapa de costo total por mes para todos los clientes
        System.out.println("Mapa de costo total por mes para todos los clientes");
        Map<String, Double> costoTotalPorMes = facturas.stream()
                .collect(Collectors.groupingBy(
                        factura -> factura.getFecha().getMonth().toString(),
                        Collectors.summingDouble(Factura::getTotal)
                ));

        // Mapa de costo total por mes para un cliente específico
        System.out.println("Mapa de costo total por mes para un cliente específico");
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
        System.out.println("Mapa de costo total por mes para clientes que no sean el cliente específico");
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

        // Paso 1: Aplana la lista de productos de todas las facturas
        /*List<Producto> todosLosProductos = facturas.stream()
                .flatMap(factura -> factura.getProductos().stream())
                .collect(Collectors.toList());*/

        // Paso 2: Crear un mapa para calcular la cantidad vendida de cada producto
        //Map<String, Double> cantidadVendidaPorNombreProducto = facturaService.getCantVendidaByProducto()

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
        model.addAttribute("empleado", empleadoLogueado);
        model.addAttribute("facturas", facturas);
        model.addAttribute("costoTotalPorMes", costoAnual);
        model.addAttribute("cantidadVendidaPorNombreProducto", new HashMap<>());
        model.addAttribute("costoTotalPorMesPersonalizado", costoAnualPersonalizado);
        model.addAttribute("costoTotalPorMesCabina", costoAnualCabina);
        return "ventas";
    }

    // Función para encontrar un producto por su ID
    private Producto encontrarProductoPorId(Long id, List<Producto> productos) {
        return productos.stream()
                .filter(producto -> Objects.equals(producto.getId(), id))
                .findFirst()
                .orElse(null);
    }
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

    @GetMapping("/clientes")
    public String clientes(Model model){
        Empleado empleadoLogueado = empleadoService.getEmpleadoLogueado();
        List<Cliente> clientes = pedidoService.clientesRegistrados();

        model.addAttribute("clientes", clientes);
        model.addAttribute("empleado", empleadoLogueado);
        return "clientes";
    }
    @PostMapping("/clientes/eliminar/{idCliente}")
    public String eliminarCleinte(@PathVariable Long idCliente, RedirectAttributes redirectAttributes) {
        int valid = pedidoService.deleteCliente(idCliente);
        if (valid < 0){
            redirectAttributes.addFlashAttribute("eliminarCliente", true);
        }
        return "redirect:/clientes";
    }


    @GetMapping("/perfil")
    public String miPerfil(Model model){
        Empleado empleadoLogueado = empleadoService.getEmpleadoLogueado();
        if(empleadoLogueado != null) {
            model.addAttribute("empleadoLog", empleadoLogueado);
            return "miPerfil";
        }
        return "error-403";
    }
    @PostMapping("/perfil/{idEmpleado}")
    public String cambiarPassword(@PathVariable Long idEmpleado, @RequestParam("currPassword") String currPassword, @RequestParam("newPassword") String newPassword, RedirectAttributes redirectAttributes, HttpSession httpSession) {
        Empleado empleado = empleadoService.getOneById(idEmpleado);

        if (empleado != null && empleado.getPassword().equals(currPassword)) {
            System.out.println("Recibi en la sesion el ID: "+httpSession.getAttribute("usuarioId"));
            empleadoService.editarContrEmpleado(empleado,newPassword);
            redirectAttributes.addFlashAttribute("passwordSuccess", true);
            return "redirect:/perfil";
        }else{
            System.out.println("Busque y no encontre el empleado: "+idEmpleado);
            redirectAttributes.addFlashAttribute("passwordError", true);
            return "redirect:/perfil";
        }
    }

    /* Probando vistas de error */
//    @GetMapping("/error403")
//    public String error403(Model model){
//        return "error-403";
//    }
//    @GetMapping("/error404")
//    public String error404(Model model){
//        return "error-404";
//    }
//    @GetMapping("/error500")
//    public String error500(Model model){
//        return "error-500";
//    }

}
