package com.example.massycake.controller;

import com.example.massycake.entities.*;
import com.example.massycake.entities.constantes.EstadoPedidoConst;
import com.example.massycake.entities.constantes.TipoPedidoConst;
import com.example.massycake.payload.request.PedidoListoRequest;
import com.example.massycake.payload.response.RecetaResponse;
import com.example.massycake.service.EmpleadoService;
import com.example.massycake.service.PedidoService;
import com.example.massycake.service.ProductoService;
import com.example.massycake.service.RecetaService;
import com.example.massycake.utils.ERole;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/cocina")
public class CocinaController {
    private final EmpleadoService empleadoService;
    private final ProductoService productoService;
    private final RecetaService recetaService;
    private final PedidoService pedidoService;

    public CocinaController(ProductoService productoService, RecetaService recetaService, PedidoService pedidoService, EmpleadoService empleadoService) {
        this.productoService = productoService;
        this.recetaService = recetaService;
        this.pedidoService = pedidoService;
        this.empleadoService = empleadoService;
    }

    @GetMapping("")
    public String cocina(Model model){
        Empleado empleadoLogueado = empleadoService.getEmpleadoLogueado();
//        List<Receta> recetas = recetaService.getAllRecetas();
        List<Pedido> pedidos = pedidoService.getPedidosPendientesByDay(3);

        List<Pedido> pedidosPendientes = new ArrayList<>();
        List<Pedido> pedidosCocina = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            if (pedido.getEstado() != null) {
                if (pedido.getEstado().equalsIgnoreCase(EstadoPedidoConst.REGISTRADO)) {
                    pedidosPendientes.add(pedido);
                } else if (pedido.getEstado().equals(EstadoPedidoConst.COCINANDO)) {
                    pedidosCocina.add(pedido);
                }
            }
        }
        int totalPedidosNoReceta = pedidoService.getCantPedidosNoReceta(pedidosPendientes);

        model.addAttribute("empleado", empleadoLogueado);
        model.addAttribute("totalPedidosNoReceta", pedidos.stream().filter(pedido -> pedido.getEstado().equals(EstadoPedidoConst.REGISTRADO) && pedido.getDetallePedido().size() <= 0).count());
        model.addAttribute("totalPedidos", pedidosPendientes.size() + pedidosCocina.size());
        model.addAttribute("pedidosPendientes", pedidosPendientes);
        model.addAttribute("pedidosCocina", pedidosCocina);
//        model.addAttribute("recetas", recetas);
        return "cocina";
    }

    @GetMapping("/confirmar/{idPedido}")
    public ResponseEntity<?> mostrarFormularioConfirmarPedido(@PathVariable("idPedido") long idPedido, Model model) {
        Pedido pedido = pedidoService.getPedidoById(idPedido);
        if (pedido.getDetallePedido() == null || pedido.getDetallePedido().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debes agregar al menos 1 detalle a tu pedido antes de marcar como listo");
        }
        for(DetallePedido detallePedido : pedido.getDetallePedido()) {
            if (detallePedido.getRecetas() == null || detallePedido.getRecetas().isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Todos los detalles deben tener al menos 1 receta");
            }
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


    @GetMapping("/pedidos")
    public String getPedidosPendientes(Model model){
        List<Pedido> pedidos = pedidoService.getAllPedidos();

        int totalPedidosPendientes = 0;
        int totalPedidosCocina = 0;

        List<Pedido> pedidosPendientes = new ArrayList<>();
        List<Pedido> pedidosCocina = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            if (pedido.getEstado() != null) {
                if (pedido.getEstado().equals(EstadoPedidoConst.REGISTRADO)) {
                    totalPedidosPendientes++;
                    pedidosPendientes.add(pedido);
                } else if (pedido.getEstado().equals(EstadoPedidoConst.COCINANDO)) {
                    totalPedidosCocina++;
                    pedidosCocina.add(pedido);
                }
            }
        }
        model.addAttribute("totalPedidosPendientes", totalPedidosPendientes);
        model.addAttribute("totalPedidosCocina", totalPedidosCocina);
        model.addAttribute("pedidosPendientes", pedidosPendientes);
        model.addAttribute("pedidosCocina", pedidosCocina);
        return "pedidos";
    }

    // Listando Recetas
    @GetMapping("/recetas")
    public String listarRecetas(Model model){
        Empleado empleadoLogueado = empleadoService.getEmpleadoLogueado();
        List<Receta> listRecetas = recetaService.getAllRecetas();
        int cantRecetasVacias = recetaService.getCantRecetasVacias();

        model.addAttribute("empleado", empleadoLogueado);
        model.addAttribute("listRecetas", listRecetas);
        model.addAttribute("cantRecetas", listRecetas.size());
        model.addAttribute("cantRecetasVacias", cantRecetasVacias);
        return "recetas";
    }

    @GetMapping("/recetas/equivalencias")
    public ResponseEntity<?> getListaEquivalencia(){

        List<Producto> ingredientes = productoService.getAllIngredientes();
        Map<Long, List<String>> medidaEquivalencia = new HashMap<>();

        for (Producto ingrediente: ingredientes) {
            List<String> conversiones = productoService.getUnidadesEquivalentes(ingrediente.getUnidadMedida());
            if (conversiones.size() >= 1){
                medidaEquivalencia.put(ingrediente.getProductoId(), conversiones);
            }else{
                conversiones.add(ingrediente.getUnidadMedida());
                medidaEquivalencia.put(ingrediente.getProductoId(), conversiones);
            }
        }

        if (!medidaEquivalencia.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(medidaEquivalencia);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al cargar equivalencias");
        }
    }

    @GetMapping("/recetas/equivalentes/{pedidoId}")
    public ResponseEntity<?> getRecetasEquivalenciaByPedido(@PathVariable("pedidoId") long pedidoid){

        Pedido pedido = pedidoService.getPedidoById(pedidoid);
        if (pedido == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pedido No Existe");
        }

        List<RecetaResponse> recetas = recetaService.getRecetasEquivalentesByPedido(pedido);
        return ResponseEntity.status(HttpStatus.OK).body(recetas);
    }

    // Create Requests (RECETA)
    @GetMapping("/recetas/crear")
    public String regReceta(Model model){
        Empleado empleadoLogueado = empleadoService.getEmpleadoLogueado();
        List<Producto> ingredientes = productoService.getAllIngredientes();
        List<String> unidades = productoService.getAllUnidades();

        model.addAttribute("empleado", empleadoLogueado);
        model.addAttribute("listaUnidades", unidades);
        model.addAttribute("ingredientes", ingredientes);
        return "regReceta";
    }
    @PostMapping("/recetas/crear")
    public String crearReceta(@Valid @ModelAttribute("receta") Receta receta, @RequestParam("pasos") String pasosJSON, @RequestParam("ingredientesJSON") String ingredientesJSON, BindingResult bindingResult, Model model){

        // PASOS
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> pasosList = new ArrayList<>();
        List<Map<String, Object>> pasos = new ArrayList<>();
        try {
            pasos = objectMapper.readValue(pasosJSON, new TypeReference<List<Map<String, Object>>>() {});
        } catch (Exception e) {
            System.out.println("ERROR EN JSON. \n"+pasosJSON);
        }

        for (Map<String, Object> paso : pasos) {
            String descripcion = (String) paso.get("descripcion");
            pasosList.add(descripcion);
        }
        receta.setPasos(pasosList);

        // INGREDIENTES
        ObjectMapper objectMapper2 = new ObjectMapper();
        List<IngredienteReceta> ingredienteRecetaList = new ArrayList<>();
        List<Map<String, Object>> ingredientesData = new ArrayList<>();
        try {
            ingredientesData = objectMapper2.readValue(ingredientesJSON, new TypeReference<List<Map<String, Object>>>(){});
        } catch (Exception e) {
            System.out.println("ERROR EN JSON. \n"+ingredientesJSON);
        }

        for (Map<String, Object> ingredienteData : ingredientesData) {
            long idProducto = Long.parseLong(String.valueOf(ingredienteData.get("idProducto")));
            float cantidad = Float.parseFloat(String.valueOf(ingredienteData.get("cantidad")));
            String unidadCantidad = String.valueOf(ingredienteData.get("unidadCantidad"));

            Producto ingrediente = productoService.getProductoById(idProducto);
            IngredienteReceta ingredienteReceta = new IngredienteReceta(ingrediente, cantidad, unidadCantidad);
            ingredienteRecetaList.add(ingredienteReceta);
        }
        receta.setIngredientes(ingredienteRecetaList);

        recetaService.createReceta(receta);

        return "redirect:/cocina/recetas";
    }

    // Edit Requests (RECETA)
    @GetMapping("/recetas/editar/{idReceta}")
    public String editReceta(@PathVariable("idReceta") long idReceta, Model model) {
        Empleado empleadoLogueado = empleadoService.getEmpleadoLogueado();
        Receta receta = recetaService.getRecetaById(idReceta);
        RecetaResponse recetaResponse = receta.getRecetaResponse();

        List<Producto> ingredientes = productoService.findAllIngredientesNotInReceta(idReceta);
        List<String> unidades = productoService.getAllUnidades();

        model.addAttribute("empleado", empleadoLogueado);
        model.addAttribute("unidades", unidades);
        model.addAttribute("receta", recetaResponse);
        model.addAttribute("ingredientes", ingredientes);
        model.addAttribute("editarReceta", true);
        return "regReceta";
    }
    @PostMapping("/recetas/editar/{idReceta}")
    public String editReceta(@Valid @ModelAttribute("receta") Receta receta, @PathVariable("idReceta") long idReceta, @RequestParam("pasos") String pasosJSON, @RequestParam("ingredientesJSON") String ingredientesJSON, BindingResult bindingResult) {
        receta.setId(idReceta);

        // PASOS
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> pasos = new ArrayList<>();
        List<String> pasosList = new ArrayList<>();
        try {
            pasos = objectMapper.readValue(pasosJSON, new TypeReference<List<Map<String, Object>>>() {});
        } catch (Exception e) {
            System.out.println("Error al convertir JSON de pasos: " +pasosJSON + "\n"+ e.getMessage());
        }

        for (Map<String, Object> paso : pasos) {
            String descripcion = (String) paso.get("descripcion");
            pasosList.add(descripcion);
        }
        receta.setPasos(pasosList);

        // INGREDIENTES
        ObjectMapper objectMapper2 = new ObjectMapper();
        List<IngredienteReceta> ingredienteRecetaList = new ArrayList<>();
        List<Map<String, Object>> ingredientesData = new ArrayList<>();
        try {
            ingredientesData = objectMapper2.readValue(ingredientesJSON, new TypeReference<List<Map<String, Object>>>(){});
        } catch (Exception e) {
            System.out.println("Error al convertir JSON de pasos: " +ingredientesJSON + "\n"+ e.getMessage());
        }

        for (Map<String, Object> ingredienteData : ingredientesData) {
            long idProducto = Long.parseLong(String.valueOf(ingredienteData.get("idProducto")));
            float cantidad = Float.parseFloat(String.valueOf(ingredienteData.get("cantidad")));
            String unidadCantidad = String.valueOf(ingredienteData.get("unidadCantidad"));

            Producto ingrediente = productoService.getProductoById(idProducto);
            IngredienteReceta ingredienteReceta = new IngredienteReceta(ingrediente, cantidad, unidadCantidad);
            ingredienteRecetaList.add(ingredienteReceta);
        }
        receta.setIngredientes(ingredienteRecetaList);

        recetaService.updateReceta(receta);
        return "redirect:/cocina/recetas";
    }

    @PostMapping("/recetas/eliminar/{idReceta}")
    public ResponseEntity<String> eliminarReceta(@PathVariable Long idReceta) {
        try {
            recetaService.deleteReceta(idReceta);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/recetas/editar/{idReceta}/{idProducto}")
    public String eliminarIngredienteReceta(@PathVariable("idReceta") long idReceta, @PathVariable("idProducto") long idProducto) {
        recetaService.deleteIngrediente(idReceta, idProducto);

        return "redirect:/recetas/editar/" + idReceta;
    }

    @GetMapping("/recetas/ver/{idReceta}")
    public String verDetallesReceta(@PathVariable("idReceta") long idReceta, @RequestParam(name = "idDetalle", required = false) Long idDetalle, Model model) {
        Empleado empleadoLogueado = empleadoService.getEmpleadoLogueado();
        Receta receta = recetaService.getRecetaById(idReceta);
        RecetaResponse recetaResponse = receta.getRecetaResponse();
        List<Receta> recetasDetalle = new ArrayList<>();

        List<Producto> ingredientes = productoService.findAllIngredientesNotInReceta(idReceta);
        List<String> unidades = productoService.getAllUnidades();
        if (idDetalle != null){
            DetallePedido detallePedido = pedidoService.getDetallePedidoById(idDetalle);
            for (Receta r1: detallePedido.getRecetas()) {
                if (r1.getId() == idReceta){
                    recetasDetalle.add(r1);
                }
            }
            detallePedido.setRecetas(recetasDetalle);
            model.addAttribute("detallePedido", detallePedido);
        }

        model.addAttribute("empleado", empleadoLogueado);
        model.addAttribute("unidades", unidades);
        model.addAttribute("receta", recetaResponse);
        model.addAttribute("ingredientes", ingredientes);
        return "verDetallesReceta";
    }

    @PostMapping("/perfil/editar/{idEmpleado}")
    public String editarRepostero(@Valid @ModelAttribute("empleado") Empleado empleado, @PathVariable("idEmpleado") long idEmpleado, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Ocurrio un error en el controlador cocina-editarRepostero: "+ bindingResult);
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
            return "redirect:/cocina";
        }
        return "redirect:/error";
    }


    //************************    MANEJADOR DE ERRORES DINAMICO    *************************//
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request, Model model) {
        String requestPath = request.getRequestURI();

        // PARA MANEJAR ERRORES EN "/recetas/crear"
        if (requestPath.matches(".*/recetas/crear") && request.getMethod().equals("POST")) {
            BindingResult bindingResult = ex.getBindingResult();

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
                    return "regReceta";
                }
            }
        }
        // end of "/recetas/crear"

        // PARA MANEJAR ERRORES EN "/ingrediente/editar/{idIngrediente}"
        if (requestPath.matches(".*/recetas/editar/\\d+") && request.getMethod().equals("POST")) {
            BindingResult bindingResult = ex.getBindingResult();
            Long idReceta = extractIdFromPath(requestPath);

            if (bindingResult.hasErrors()) {
                System.out.println("\n\nHubo un error en handleValidationException\n\n");
                FieldError fieldError = bindingResult.getFieldError();
                String fieldName;
                String errorMessage;

                if (fieldError != null) {
                    Receta receta = recetaService.getRecetaById(idReceta);
                    RecetaResponse recetaResponse = receta.getRecetaResponse();
                    List<Producto> ingredientes = productoService.findAllIngredientesNotInReceta(idReceta);

                    fieldName = fieldError.getField();
                    errorMessage = fieldError.getDefaultMessage();
                    model.addAttribute("campo", fieldName);
                    model.addAttribute("mensaje", errorMessage);
                    model.addAttribute("typeError", true);

                    model.addAttribute("receta", recetaResponse);
                    model.addAttribute("ingredientes", ingredientes);
                    model.addAttribute("editarReceta", true);
                    return "regReceta";
                }
            }
        }
        // end of "/recetas/editar/{idReceta}"

        // PARA MANEJAR ERRORES GENERALES (DEFAULT)
        return "redirect:/400";
    }

    private Long extractIdFromPath(String path) {
        String[] pathParts = path.split("/");
        return Long.parseLong(pathParts[pathParts.length - 1]);
    }
}
