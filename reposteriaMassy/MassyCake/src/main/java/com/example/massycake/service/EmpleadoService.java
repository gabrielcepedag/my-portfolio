package com.example.massycake.service;

import com.example.massycake.entities.Empleado;
import com.example.massycake.repository.EmpleadoRepo;
import com.example.massycake.utils.ERole;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

    public Optional<Empleado> getOneByUsername(String username) {
        return empleadoRepo.findByUsuario(username);
    }

    public List<String> getAllRoles() {
        //Temporal con roles estaticos en la aplicacion
        List<String> roles = new ArrayList<>();
        for (ERole rol : ERole.values()) {
            roles.add(rol.name());
        }
        return roles;
    }

    public record EmpleadoResponse(long empleadoId, String cedula, String nombre, String apellido, String usuario,
                                   String telefono, String direccion, String rol, boolean eliminado){}
    private final EmpleadoRepo empleadoRepo;

    public EmpleadoService(EmpleadoRepo empleadoRepo) {
        this.empleadoRepo = empleadoRepo;
    }

    public List<EmpleadoResponse> getAllEmpleados(int limit, int offset) {
        List<Empleado> empleados = empleadoRepo.myFindALl();
        List<EmpleadoResponse> responseList = new ArrayList<>();

        for (Empleado emp: empleados) {
            responseList.add(new EmpleadoResponse(emp.getEmpleadoId(), emp.getCedula(), emp.getNombre(), emp.getApellido(), emp.getUsuario(),
                    emp.getTelefono(), emp.getDireccion(),emp.getRol(), emp.isEliminado()));
        }

        return responseList;
    }

    public int getCantEmpleados(){
        return empleadoRepo.countEmpleado();
    }
    public int getCantCajeros(){
        return empleadoRepo.getCantCajeros();
    }
    public int getCantReposteros(){
        return empleadoRepo.getCantReposteros();
    }
    public int createEmpleado(Empleado empleado) {
        // CODES: -1 = Error en Cedula | 1 = Operación Exitosa | -2 = Error en Username
        int code = -1;

        if(empleadoRepo.findByCedula(empleado.getCedula()).isEmpty()){
            if(empleadoRepo.findByUsuario(empleado.getUsuario()).isEmpty()) {
                code = 1;
                empleadoRepo.save(empleado);
            } else {
                code = -2;
            }
        }
        return code;
    }

    public EmpleadoResponse deleteEmpleado(Long id) {
        Empleado emp = empleadoRepo.findById(id).orElse(null);
        if (emp == null){
            return null;
        }
        emp.setEliminado(true);
        empleadoRepo.save(emp);
        return new EmpleadoResponse(emp.getEmpleadoId(), emp.getCedula(), emp.getNombre(), emp.getApellido(), emp.getUsuario(),
                emp.getTelefono(), emp.getDireccion(),emp.getRol(), emp.isEliminado());
    }

    public int editarEmpleado(Empleado empleado) {

        Empleado local = empleadoRepo.findById(empleado.getEmpleadoId()).orElse(null);
        // CODES: 0 = Error de Empleado No Existe | 1 = Operación Exitosa
        int code = 0;

        if (local == null){
            System.out.println("Empleado con ID: "+empleado.getEmpleadoId()+ " No existe");
            return code;
        }
        code = 1;

        if (empleado.getPassword().isEmpty()){
            empleado.setPassword(local.getPassword());
        }

        Empleado emp = empleadoRepo.save(empleado);
//        EmpleadoResponse erp = new EmpleadoResponse(emp.getEmpleadoId(), emp.getCedula(), emp.getNombre(), emp.getApellido(), emp.getUsuario(),
//                emp.getTelefono(), emp.getDireccion(),emp.getRol(), emp.isEliminado());

        return code;
    }

    public void editarContrEmpleado(Empleado empleado, String newPassword) {

        Empleado local = empleadoRepo.findById(empleado.getEmpleadoId()).orElse(null);

        if (local == null){
            System.out.println("Empleado con ID: "+empleado.getEmpleadoId()+ " No existe");
            return;
        }

        empleado.setPassword(newPassword);
        Empleado emp = empleadoRepo.save(empleado);
    }

    public EmpleadoResponse getOneEmpleadoResponse(Long idEmpleado) {
        Empleado emp = empleadoRepo.findById(idEmpleado).orElse(null);
        if (emp == null){
            return null;
        }
        return new EmpleadoResponse(emp.getEmpleadoId(), emp.getCedula(), emp.getNombre(), emp.getApellido(), emp.getUsuario(),
                emp.getTelefono(), emp.getDireccion(),emp.getRol(), emp.isEliminado());
    }

    public Empleado getOneById(Long id){
        return empleadoRepo.findById(id).orElse(null);
    }
    public EmpleadoResponse hacerLogin(String usuario, String password) {
        boolean login = false;
        Empleado emp = empleadoRepo.findByUsuario(usuario).orElse(null);

        if (emp != null){
            if (emp.getPassword().equals(password)){
                login = true;
            }
        }
        if (login){
            return new EmpleadoResponse(emp.getEmpleadoId(), emp.getCedula(), emp.getNombre(), emp.getApellido(), emp.getUsuario(),
                    emp.getTelefono(), emp.getDireccion(),emp.getRol(), emp.isEliminado());
        }
        return null;
    }

    public Empleado getEmpleadoLogueado(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null){
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            return getOneByUsername(userDetails.getUsername()).orElse(null);
        }
        return null;
    }
//    public Empleado getEmpleadoByCedula(String cedula) {
//        String queryStr = "SELECT e FROM Empleado e WHERE e.cedula = :cedula";
//        TypedQuery<Empleado> query = entityManager.createQuery(queryStr, Empleado.class);
//        query.setParameter("cedula", cedula);
//
//        try {
//            return query.getSingleResult();
//        } catch (NoResultException e) {
//            return null; // Return null if no employee with the given cedula is found
//        }
//    }

}