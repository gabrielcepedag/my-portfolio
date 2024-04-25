package com.example.massycake.service;

import com.example.massycake.entities.*;
import com.example.massycake.entities.constantes.EstadoPedidoConst;
import com.example.massycake.repository.ClienteRepo;
import com.example.massycake.repository.DetallePedidoRepo;
import com.example.massycake.repository.PedidoRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PedidoService {

    private final PedidoRepo pedidoRepo;
    private final ClienteRepo clienteRepo;
    private final EmpleadoService empleadoService;
    private final RecetaService recetaService;
    private final DetallePedidoRepo detallePedidoRepo;

    public PedidoService(PedidoRepo pedidoRepo, ClienteRepo clienteRepo, EmpleadoService empleadoService, RecetaService recetaService, DetallePedidoRepo detallePedidoRepo) {
        this.pedidoRepo = pedidoRepo;
        this.clienteRepo = clienteRepo;
        this.empleadoService = empleadoService;
        this.recetaService = recetaService;
        this.detallePedidoRepo = detallePedidoRepo;
    }

    public List<Pedido> getAllPedidos() {
        return pedidoRepo.myFindAllFromNow(LocalDateTime.now());
    }
    public Pedido getPedidoById(long idPedido) {
        return pedidoRepo.findById(idPedido).orElse(null);
    }

    public int createPedido(Pedido pedido, Cliente cliente) {
        int valid = 0;

        //Empleado temporal logueado:
        Empleado emp = empleadoService.getOneById(1L);
        if (emp == null) {
            valid = -2; //Empleado no existe
        }
        System.out.println("Pedido tiene descripcion: " + pedido.getDescripcion() + " ... y tipo es: " + pedido.getTipoPedido() + " ... INICIAL: " + pedido.getInicial());
        System.out.println("Size de detalle pedido es: " + pedido.getDetallePedido().size());

        Pedido newPedido = new Pedido(pedido.getTipoPedido(), pedido.getNombre(), pedido.getFechaEntrega(), EstadoPedidoConst.REGISTRADO,
                pedido.getDescuento(), pedido.getDescripcion(), pedido.getMetodoEntrega(), cliente, emp);

        newPedido.setCostoTotal(pedido.getCostoTotal());
        newPedido.setInicial(pedido.getInicial());

        for (DetallePedido detalle : pedido.getDetallePedido()) {
            newPedido.addDetalle(detalle);
            detalle.setPedido(newPedido);
        }

        if (newPedido.getDetallePedido().size() < 1) {
            valid = -3; //Pedido sin detalle
        }

        try {
            pedidoRepo.save(newPedido);
            valid = 1;
        } catch (Exception e) {
            e.printStackTrace();
            valid = -1; //Error al guardar el Pedido en Base de Datos
        }

        return valid;
    }

    public int createPedidoCabina(Pedido pedido) {
        int valid = 0;

        //Empleado temporal logueado:
        Empleado emp = empleadoService.getOneById(1L);
        if (emp == null) {
            valid = -2; //Empleado no existe
        }
        pedido.setEmpleado(emp);

//        pedido.setCliente(getClienteMassy().get());

        System.out.println(pedido.getPedidoId() + "   IDDDD\n\n");
        try {
            pedidoRepo.save(pedido);
            valid = 1;
        } catch (Exception e) {
            e.printStackTrace();
            valid = -1; //Error al guardar el Pedido en Base de Datos
        }

        return valid;
    }

    public Cliente getClienteMassy() {
        Cliente cliente = new Cliente();
        if(clienteRepo.findByCedula("000-0000000-0").isEmpty()) {
            cliente.setCedula("000-0000000-0");
            cliente.setNombre("Massys Cake");
            clienteRepo.save(cliente);
            return cliente;
        }

        return clienteRepo.findByCedula("000-0000000-0").orElse(null);
    }

    public Cliente getClienteCabina() {
        Cliente cliente = new Cliente();
        if(clienteRepo.findByCedula("111-1111111-1").isEmpty()) {
            cliente.setCedula("111-1111111-1");
            cliente.setNombre("Massys Cake - pedido cabina");
            clienteRepo.save(cliente);
            return Optional.of(cliente).get();
        }

        return clienteRepo.findByCedula("111-1111111-1").get();
    }

    public Pedido updatePedido(Pedido pedido) {
        // Aquí puedes agregar lógica adicional antes de actualizar el pedido, si es necesario
        return pedidoRepo.save(pedido);
    }

    public int deletePedido(long idPedido) {
        Pedido pedido = pedidoRepo.findById(idPedido).orElse(null);
        if (pedido == null) {
            return -1; //No encontrado
        }
        pedido.setEliminado(true);
        pedidoRepo.save(pedido);
        return 1;
    }

    @Transactional
    public int crearCliente(Cliente cliente) {
        // CODES: 0 = Error en Cédula | 1 = Operación Exitosa | 2 = Error en Nombre
        int code = 0;

        if (clienteRepo.findByCedula(cliente.getCedula()).isEmpty()) {
            code = 1;
            cliente.setFechaCreacion(Date.valueOf(LocalDate.now()));
            clienteRepo.save(cliente);
        } else {
            code = 0;
        }

        return code;
    }

    public Optional<Cliente> findByCedula(String cedula) {
        return clienteRepo.findByCedula(cedula);
    }


    public List<Cliente> clientesRegistrados() {
        return clienteRepo.myFindALl();
    }

    public Cliente getClienteById(Long clienteId) {
        return clienteRepo.findById(clienteId).orElse(null);
    }

    public List<DetallePedido> convert(String json, Pedido pedido) {
        List<DetallePedido> lista = new ArrayList<>();

        JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();
        Gson gson = new Gson();

        for (JsonElement jsonElement : jsonArray) {
            DetallePedido detallePedido = new DetallePedido();

            JsonObject detalleObject = jsonElement.getAsJsonObject();

            detallePedido.setMasa(detalleObject.get("tipoMasa").getAsString());
            detallePedido.setForma(detalleObject.get("forma").getAsString());
            detallePedido.setRelleno(detalleObject.get("relleno").getAsString());
            detallePedido.setPeso(Float.parseFloat(detalleObject.get("peso").getAsString()));
            detallePedido.setUnidad(detalleObject.get("unidad").getAsString());
            detallePedido.setNota(detalleObject.get("notas").getAsString());
            detallePedido.setCosto(Float.parseFloat(detalleObject.get("costo").getAsString()));
            detallePedido.setPedido(pedido); // Asigna el pedido correspondiente si está disponible

            lista.add(detallePedido);
        }

        return lista;
    }

    public int deleteCliente(Long idCliente) {
        int valid = 0;

        try {
            Cliente cliente = clienteRepo.findById(idCliente).orElse(null);
            if (cliente == null) {
                return -1; //No existe
            }
            cliente.setEliminado(true);
            clienteRepo.save(cliente);
            valid = 1;
        } catch (Exception e) {
            e.printStackTrace();
            valid = -2; //Error al eliminar cliente.
        }
        return valid;
    }

    public List<String> getUnidadesPeso() {
        List<String> unidadesPeso = pedidoRepo.getUnidadesByTipo("peso");
        int index = unidadesPeso.indexOf("Lb");
        if (index != -1) {
            Collections.swap(unidadesPeso, 0, index);
        }
        return unidadesPeso;
    }

    public Float getFactorConversion(String origen, String resultante) {
        Float factor;
        factor = pedidoRepo.findFactor(origen, resultante);
        return factor;
    }

    public List<Map<String, Object>> parseDetallesPedido(String detallesPedidos) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> detallesData = new ArrayList<>();
        try {
            detallesData = objectMapper.readValue(detallesPedidos, new TypeReference<List<Map<String, Object>>>(){});
        } catch (Exception e) {
            System.out.println("\n\n"+detallesData);
            System.out.println("Error al convertir JSON de detalles: " + e.getMessage());
        }
        return detallesData;
    }

    public int editarDetallesPedido(List<Map<String, Object>> detallesData, Pedido pedido) {
        //CODIGOS DE RETORNO: 1 -> Operación exitosa
        //                    0 -> Error de creación (RecetaPedido)
        //                   -1 -> Error de factor (RecetaPedido)
        //                   -2 -> Error al guardar pedido
        //                   -3 -> Error general

        for (Map<String, Object> ingredienteData : detallesData) {
            long idReceta = Long.parseLong(String.valueOf(ingredienteData.get("idReceta")));
            long idDetalle = Long.parseLong(String.valueOf(ingredienteData.get("idDetalle")));

            Receta auxReceta = recetaService.getRecetaById(idReceta);
            System.out.println("RECETA RECIBIDA: "+auxReceta.getId());

            DetallePedido auxDetalle = null;
            for (DetallePedido detalle : pedido.getDetallePedido()) {
                if (detalle.getDetalle_pedidoId() == idDetalle) {
                    auxDetalle = detalle;
                    break;
                }
            }
            if(auxDetalle != null){
                System.out.println("DETALLE RECIBIDO: "+auxDetalle.getDetalle_pedidoId());
                boolean recetaExists = false;
                for (Receta receta : auxDetalle.getRecetas()) {
                    if (Objects.equals(receta.getId(), auxReceta.getId())) {
                        recetaExists = true;
                        break;
                    }
                }
                int creado = 1;
                if (!recetaExists) {
                    auxDetalle.addReceta(auxReceta);
                    creado = this.createRecetaPedido(auxReceta, auxDetalle, pedido);
                }else{
                    System.out.println("La receta recibida ya existe en este detalle.\n");
                }

                if(creado != 1){
                    return creado;
                }else{
                    System.out.println("||============================================================================||");
                    System.out.println("DetallePedidoReceta creado exitosamente.");
                    for(Receta rec : auxDetalle.getRecetas()){
                        System.out.println("RECETA: "+rec.getId());
                    }

                    System.out.println("RecetaPedido creado exitosamente. [RECETA: "+auxReceta.getNombre()+" | DETALLE: "+auxDetalle.getDetalle_pedidoId()+" | PEDIDO: "+pedido.getPedidoId()+"]");
                    for(PedidoIngrediente pedidoIngrediente : auxDetalle.getIngredientes()){
                        System.out.println("INGREDIENTE: "+pedidoIngrediente.getIngrediente().getNombre());
                    }
                    System.out.println("||============================================================================||");
                    if(this.updatePedido(pedido) != null){
                        return 1; //Operación exitosa
                    }else{
                        return -2;
                    }

                }
            }else{
                System.out.println("ERROR: El detalle no ha sido encontrado. (ID: "+idDetalle+")");
                return -3;
            }

        }
        return -3;
    }

    public List<?> isPedidoOnlyUnds(Pedido pedido, List<DetallePedido> detallesPedidos) {
        List<?> recetas;
        boolean allNonUnds = true;
        boolean onlyUnds = true;

        for (DetallePedido detallePedido : detallesPedidos) {
            if (!"Unds".equalsIgnoreCase(detallePedido.getUnidad())) {
                onlyUnds = false;
            } else {
                allNonUnds = false;
            }
            if (!allNonUnds && !onlyUnds) {
                break;
            }
        }

        if (allNonUnds) {
            recetas = recetaService.getRecetasNotUnds();
        } else if (onlyUnds) {
            recetas = recetaService.getRecetasEquivalentesByPedido(pedido);
        } else {
            recetas = recetaService.getAllRecetas();
        }

        return recetas;
    }

    public List<Cliente> getClientes() {
        return clienteRepo.findAll();
    }

    public List<Pedido> getPedidosPendientesByDay(int days) {
        return pedidoRepo.findAllPendientesByDays(LocalDateTime.now(), days);
    }

    public int getCantPedidosNoReceta(List<Pedido> pedidosPendientes) {
        int cant = 0;
        for (Pedido p1 : pedidosPendientes) {
            int recetas = pedidoRepo.findCantReceta(p1.getPedidoId());
            if (recetas == 0){
                cant++;
            }
        }
        return cant;
    }

    public int createRecetaPedido(Receta auxReceta, DetallePedido auxDetalle, Pedido pedido) {
        //CODIGOS DE RETORNO: 1 -> Creación correcta || 0 -> Error de creación || -1 -> Error de factor
        Float factor;
        float nuevaPorcionReceta;

        System.out.println("UNIDAD RECETA: "+auxReceta.getUniPorcionResultante());
        System.out.println("UNIDAD DETALLE: "+auxDetalle.getUnidad());

        if (!auxReceta.getUniPorcionResultante().equalsIgnoreCase(auxDetalle.getUnidad())) {
            factor = getFactorConversion(auxReceta.getUniPorcionResultante(), auxDetalle.getUnidad());
            if (factor == null) {
                return -1; // Error de factor
            }
            nuevaPorcionReceta = factor * auxReceta.getPorcionResultante();
            factor = auxDetalle.getPeso() / nuevaPorcionReceta;
        } else {
            factor = auxDetalle.getPeso() / auxReceta.getPorcionResultante();
        }

        for (IngredienteReceta ingredienteReceta : auxReceta.getIngredientes()) {
            float cantIngrediente = Math.round(factor * ingredienteReceta.getCantidad() * 10) / 10.0f;
            PedidoIngrediente newIngrediente = new PedidoIngrediente(ingredienteReceta.getIngrediente(), cantIngrediente, ingredienteReceta.getUnidadCantidad());
            System.out.println("== INGREDIENTE AGREGADO: " + ingredienteReceta.getIngrediente().getNombre() + " (Cant. " + cantIngrediente + " " + ingredienteReceta.getUnidadCantidad() + ") ==");

            if (newIngrediente != null) {
                boolean found = false;
//                for (PedidoIngrediente existingIngrediente : pedido.getIngredientes()) {
//                    if (existingIngrediente.getIngrediente().equals(newIngrediente.getIngrediente())) {
//                        existingIngrediente.setCantidad(existingIngrediente.getCantidad() + newIngrediente.getCantidad());
//                        found = true;
//                        break;
//                    }
//                }
                for (PedidoIngrediente existingIngrediente : auxDetalle.getIngredientes()) {
                    if (existingIngrediente.getIngrediente().equals(newIngrediente.getIngrediente())) {
                        existingIngrediente.setCantidad(existingIngrediente.getCantidad() + newIngrediente.getCantidad());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    auxDetalle.addIngrediente(newIngrediente);
                }
            } else {
                return 0; // Error en la creacion
            }
        }
        updatePedido(pedido);
        return 1; // Creacion exitosa
    }

    public int removeRecetaPedido(Receta auxReceta, DetallePedido auxDetalle, Pedido pedido) {
        //CODIGOS DE RETORNO: 1 -> Creación correcta || 0 -> Error de creación PedidoIngrediente || -1 -> Error de factor
        Float factor;

        if (!auxReceta.getUniPorcionResultante().equalsIgnoreCase(auxDetalle.getUnidad())) {
            factor = getFactorConversion(auxReceta.getUniPorcionResultante(), auxDetalle.getUnidad());
            if (factor == null) {
                return -1;
            }
            float nuevaPorcionReceta = factor * auxReceta.getPorcionResultante();
            factor = auxDetalle.getPeso() / nuevaPorcionReceta;
        } else {
            factor = auxDetalle.getPeso() / auxReceta.getPorcionResultante();
        }

        for (IngredienteReceta ingredienteReceta : auxReceta.getIngredientes()) {
            float cantIngrediente = Math.round(factor * ingredienteReceta.getCantidad() * 10) / 10.0f;
            PedidoIngrediente removedIngrediente = new PedidoIngrediente(ingredienteReceta.getIngrediente(), cantIngrediente, ingredienteReceta.getUnidadCantidad());

            if (removedIngrediente != null) {
                for (PedidoIngrediente existingIngrediente : auxDetalle.getIngredientes()) {
                    if (existingIngrediente.getIngrediente().equals(removedIngrediente.getIngrediente())) {
                        existingIngrediente.setCantidad(existingIngrediente.getCantidad() - removedIngrediente.getCantidad());
                        if (existingIngrediente.getCantidad() <= 0) {
                            auxDetalle.getIngredientes().remove(existingIngrediente);
                        }
                        break;
                    }
                }
            } else {
                return 0;
            }
        }
        updatePedido(pedido);
        return 1;
    }


    public DetallePedido getDetalleFromPedidoById(List<DetallePedido> detallePedido, long idDetalle) {
        for (DetallePedido detalle : detallePedido) {
            if (detalle.getDetalle_pedidoId() == idDetalle) {
                return detalle;
            }
        }
        return null;
    }

    public DetallePedido getDetallePedidoById(Long id){
        return detallePedidoRepo.findById(id).orElse(null);
    }

}
