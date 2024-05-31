package com.example.massycake.controller;

import com.example.massycake.entities.*;
import com.example.massycake.entities.constantes.EstadoPedidoConst;
import com.example.massycake.entities.constantes.TipoPedidoConst;
import com.example.massycake.payload.request.*;
import com.example.massycake.service.EmpleadoService;
import com.example.massycake.service.PedidoService;
import com.example.massycake.service.ProductoService;
import com.example.massycake.service.RecetaService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {
    private ModelMapper modelMapper = new ModelMapper();
    private final PedidoService pedidoService;
    private final EmpleadoService empleadoService;

    private final RecetaService recetaService;

    @Autowired
    ProductoService productoService;


    public PedidoController(PedidoService pedidoService, EmpleadoService empleadoService, RecetaService recetaService) {
//      this.modelMapper = modelMapper;
        this.pedidoService = pedidoService;
        this.empleadoService = empleadoService;
        this.recetaService = recetaService;

    }

    // Mostrar lista de pedidos
    @GetMapping("")
    public String listarPedidos(Model model, @RequestParam(required = false) String paginacion) {
        List<Pedido> pedidos = new ArrayList<>();

        if (paginacion != null && paginacion.equalsIgnoreCase("true")){
            pedidos = pedidoService.getPedidosPendientesByDay(7);
        }else{
             pedidos = pedidoService.getAllPedidos();
        }
        Empleado empleadoLogueado = empleadoService.getEmpleadoLogueado();

        // Obtener la lista de pedidos desde el servicio

        int totalPedidosDespachados = 0;
        List<Pedido> pedidosPendientes = new ArrayList<>();
        List<Pedido> pedidosCocina = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            if (pedido.getEstado() != null) {
                if (pedido.getEstado().equals(EstadoPedidoConst.REGISTRADO)) {
                    pedidosPendientes.add(pedido);
                } else if (pedido.getEstado().equals(EstadoPedidoConst.COCINANDO)) {
                    pedidosCocina.add(pedido);
                } else if (pedido.getEstado().equalsIgnoreCase(EstadoPedidoConst.DESPACHADO)){
                    totalPedidosDespachados++;
                }
            }
        }

        model.addAttribute("empleado", empleadoLogueado);
        model.addAttribute("totalPedidosPendientes", pedidosPendientes.size());
        model.addAttribute("totalPedidosDespachados", totalPedidosDespachados);
        model.addAttribute("totalPedidosCocina", pedidosCocina.size());
        model.addAttribute("pedidosPendientes", pedidosPendientes);
        model.addAttribute("pedidosCocina", pedidosCocina);
        return "pedidos";
    }

    // Crear pedido
    @GetMapping("/crear")
    public String mostrarFormularioCrearPedido(Model model) {
        Empleado empleadoLogueado = empleadoService.getEmpleadoLogueado();
        List<Cliente> clientes = pedidoService.clientesRegistrados();
        List<String> unidadesPeso = pedidoService.getUnidadesPeso();

        model.addAttribute("empleado", empleadoLogueado);
        model.addAttribute("unidadesPeso", unidadesPeso);
        model.addAttribute("clientes", clientes);
        model.addAttribute("pedido", new Pedido());
        model.addAttribute("pedidoRequest", new PedidoRequest());
        model.addAttribute("clienteRequest", new ClienteRequest());
        model.addAttribute("detallePedidoRequest", new ArrayList<DetallePedidoRequest>());
        return "regPedido";
    }

    @PostMapping("/crearCliente")
    public ResponseEntity<?> crearCliente(@Valid @RequestBody ClienteRequest clienteRequest, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            System.out.println("Ocurrio un error en el controlador pedido-crearCliente: "+ bindingResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldError().getDefaultMessage());
        }

        Cliente newCliente = new Cliente(clienteRequest.getCedula(), clienteRequest.getNombre(), clienteRequest.getDireccion(), clienteRequest.getTelefono());
        int result = pedidoService.crearCliente(newCliente);

        if (result <= 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Esta cedula ya está registrada, ingresa otra");
        }

        return ResponseEntity.status(HttpStatus.OK).body(clienteRequest);
    }


    @PostMapping("/crear")
    public ResponseEntity<?> crearPedido(@Valid @RequestBody PedidoRequest pedidoRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            System.out.println("Ocurrio un error en el controlador pedido-crearPedido: "+bindingResult.getFieldError().getDefaultMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldError().getDefaultMessage());
        }

        Optional<Cliente> cliente = pedidoService.findByCedula(pedidoRequest.getCliente().substring(0, pedidoRequest.getCliente().indexOf('|')-1));
        if (cliente.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente no existe.");
        }

        Pedido pedido = modelMapper.map(pedidoRequest, Pedido.class);
        pedido.setCostoTotal(pedidoRequest.getCostoTotal());
        pedido.setInicial(pedidoRequest.getInicial());
        pedido.setMetodoEntrega(pedidoRequest.getTipoEntrega());
        pedido.setDescuento(pedidoRequest.getDescuento());

        int valid = pedidoService.createPedido(pedido, cliente.get());
        if (valid < 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al guardar el pedido. [Valid: "+valid+"]");
        }
        return ResponseEntity.status(HttpStatus.OK).body(pedidoRequest);
    }

    @PostMapping("/crear-cabina")
    public ResponseEntity<?> crearPedidoCabina(@Valid @RequestBody PedidoCabinaRequest pedidoCabinaRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Error al crear pedido de cabina.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldError().getDefaultMessage());
        }

        Pedido pedidoCabina = modelMapper.map(pedidoCabinaRequest, Pedido.class);
        pedidoCabina.setTipoPedido(TipoPedidoConst.CABINA);

        int valid = pedidoService.createPedido(pedidoCabina, pedidoService.getClienteMassy());
        if (valid < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al guardar el pedido de cabina.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(pedidoCabinaRequest);
    }

    @GetMapping("/ver/{idPedido}")
    public String mostrarDetallesPedido(@PathVariable("idPedido") long idPedido, Model model) {
        Empleado empleadoLogueado = empleadoService.getEmpleadoLogueado();
        Pedido pedido = pedidoService.getPedidoById(idPedido);
        List<DetallePedido> detallesPedidos = pedido.getDetallePedido();

        model.addAttribute("empleado", empleadoLogueado);
        model.addAttribute("pedido", pedido);
        model.addAttribute("detallesPedido", detallesPedidos);

        return "verDetallesPedido";
    }

    @GetMapping("/editar/{idPedido}")
    public String mostrarFormularioEditarPedido(@PathVariable("idPedido") long idPedido, Model model) {
        Empleado empleadoLogueado = empleadoService.getEmpleadoLogueado();
        Pedido pedido = pedidoService.getPedidoById(idPedido);
        List<DetallePedido> detallesPedidos = pedido.getDetallePedido();
        List<?> recetas = pedidoService.isPedidoOnlyUnds(pedido, detallesPedidos);

        model.addAttribute("empleado", empleadoLogueado);
        model.addAttribute("recetas", recetas);
        model.addAttribute("pedido", pedido);
        model.addAttribute("detallesPedido", detallesPedidos);

        return "addRecetasPedido";
    }

    @PostMapping("/editar/{idPedido}")
    public String completarEditarPedido(@PathVariable("idPedido") long idPedido, @RequestParam("recetasJSON") String detallesPedidosJson) {
        Pedido pedido = pedidoService.getPedidoById(idPedido);
        pedido.setPedidoId(idPedido);

        // Parseando el JSON recibido
        List<Map<String, Object>> detallesData = pedidoService.parseDetallesPedido(detallesPedidosJson);

        // Revisando si el JSON recibido está vacío (se eliminaron todas las recetas)
        if (detallesData.isEmpty()) {
            // Eliminando todas las recetas de DetallePedido y sus ingredientes de Pedido
            for (DetallePedido detalle : pedido.getDetallePedido()) {
                List<Receta> auxRecetaList = new ArrayList<>(detalle.getRecetas());
                detalle.getRecetas().clear();

                for (Receta receta : auxRecetaList) {
                    pedidoService.removeRecetaPedido(receta, detalle, pedido);
                }
            }
            return "redirect:/cocina";
        }

        // En caso contrario, Recorriendo las tuplas de Receta-Detalle del JSON recibido
        for (Map<String, Object> ingredienteData : detallesData) {
            long idReceta = Long.parseLong(String.valueOf(ingredienteData.get("idReceta")));
            long idDetalle = Long.parseLong(String.valueOf(ingredienteData.get("idDetalle")));

            // Buscando receta recibida
            Receta auxReceta = recetaService.getRecetaById(idReceta);
            auxReceta.setId(idReceta);

            // Buscando detalle recibido
            DetallePedido auxDetalle = pedidoService.getDetalleFromPedidoById(pedido.getDetallePedido(), idDetalle);

            if (auxDetalle != null) {
                auxDetalle.setDetalle_pedidoId(idDetalle);

                // Identificando si se ha eliminado recetas, y cuales
                List<Receta> deletedRecipes = new ArrayList<>();
                for (Receta existingReceta : auxDetalle.getRecetas()) {
                    boolean existsInNewList = false;
                    for (Map<String, Object> detalleData : detallesData) {
                        long idRecetaInData = Long.parseLong(String.valueOf(detalleData.get("idReceta")));
                        if (Objects.equals(existingReceta.getId(), idRecetaInData)) {
                            existsInNewList = true;
                            break;
                        }
                    }
                    if (!existsInNewList) {
                        deletedRecipes.add(existingReceta);
                    }
                }
                // Eliminando las recetas identificadas del DetallePedido, y los Ingredientes del Pedido
                for (Receta deletedReceta : deletedRecipes) {
                    auxDetalle.removeReceta(deletedReceta);
                    int removed = pedidoService.removeRecetaPedido(deletedReceta, auxDetalle, pedido);
                    if(removed != 1){
                        System.out.println("ERROR: La eliminación de RecetaPedido ha retornado 0 o -1. [" + removed + "]");
                        return "redirect:/error";
                    }
                }

                // Identificando si de las recetas restantes, alguna ya existía previamente
                boolean recetaExists = false;
                for (Receta receta : auxDetalle.getRecetas()) {
                    if (Objects.equals(receta.getId(), auxReceta.getId())) {
                        recetaExists = true;
                        break;
                    }
                }

                int creado = 1;
                // Si no existía, agregarla al detalle y sus ingredientes al pedido.
                if (!recetaExists) {
                    auxDetalle.addReceta(auxReceta);
                    creado = pedidoService.createRecetaPedido(auxReceta, auxDetalle, pedido);
                }
                if (creado != 1) {
                    System.out.println("ERROR: La creación de RecetaPedido ha retornado 0 o -1. [" + creado + "]");
                    return "redirect:/error";
                }
            } else {
                System.out.println("ERROR: El detalle no ha sido encontrado. (ID: " + idDetalle + ")");
            }
        }
        return "redirect:/pedidos?paginacion=true";
    }


    // Eliminar pedido
    @PostMapping("/eliminar/{idPedido}")
    public ResponseEntity<String> eliminarPedido(@PathVariable("idPedido") long idPedido) {
        pedidoService.deletePedido(idPedido);

        return ResponseEntity.ok("Pedido eliminado correctamente");
    }

    @GetMapping("/confirmar/{idPedido}")
    public ResponseEntity<?> mostrarFormularioConfirmarPedido(@PathVariable("idPedido") long idPedido, Model model) {
        Pedido pedido = pedidoService.getPedidoById(idPedido);
        if (pedido.getDetallePedido() == null || pedido.getDetallePedido().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debes agregar al menos 1 detalle a tu pedido antes de marcar como listo");
        }
        pedido.setEstado(EstadoPedidoConst.COCINANDO);
        pedidoService.updatePedido(pedido);

        return ResponseEntity.ok("Pedido enviado confirmado correctamente");
    }

    @PostMapping("/listo")
    public ResponseEntity<?> marcarPedidoListo(@RequestBody PedidoListoRequest pedidoListoRequest) {

        // Obtener el pedido por su ID (puedes implementar tu lógica aquí)
        Pedido pedido = pedidoService.getPedidoById(pedidoListoRequest.getIdPedido());

        if (pedido == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido no encontrado.");
        }

        pedido.setEstado(EstadoPedidoConst.LISTO);

        Producto producto = new Producto();
        producto.setNombre(pedido.getNombre());
        producto.setDescripcion(pedido.getDescripcion());
        producto.setDescuento(pedido.getDescuento());
        producto.setCategoria(pedidoListoRequest.getCategoria());
        producto.setCantidad(pedidoListoRequest.getCantidad());
        producto.setPrecio(pedidoListoRequest.getPrecio());
        producto.setDispMin(pedidoListoRequest.getDispMin());
        producto.setDispMin(pedidoListoRequest.getDispMin());
        producto.setTipo(TipoPedidoConst.CABINA);
        producto.setFoto(pedidoListoRequest.getFotoBase64());

        // Guardar el pedido actualizado en la base de datos
        productoService.guardarProducto(producto);

        return ResponseEntity.status(HttpStatus.OK).body("Pedido marcado como listo.");
    }

    @PostMapping("/listoPersonalizado")
    public ResponseEntity<?> marcarPedidoListoPersonalizado(@RequestBody Map<String, Long> pedidoData) {
        try {
            Long idPedido = pedidoData.get("idPedido");
            Pedido pedido = pedidoService.getPedidoById(idPedido);

            if (pedido == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido no encontrado.");
            }

            pedido.setEstado(EstadoPedidoConst.LISTO);

            Producto producto = new Producto();
            producto.setNombre(pedido.getNombre());
            producto.setDescripcion(pedido.getDescripcion());
            producto.setDescuento(pedido.getDescuento());
            producto.setCategoria("Personalizado");
            producto.setCantidad(1);
            producto.setPrecio(pedido.getCostoTotal().doubleValue());
            producto.setDispMin(0);
            producto.setTipo(TipoPedidoConst.PERSONALIZADO);

            productoService.guardarProducto(producto);

            return ResponseEntity.status(HttpStatus.OK).body("Pedido marcado como listo.");
        } catch (Exception e) {
            // Manejo de errores: Puedes personalizar el mensaje de error o realizar otras acciones aquí
            e.printStackTrace(); // Imprime el error en la consola para depuración
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al marcar el pedido como listo.");
        }
    }



}
