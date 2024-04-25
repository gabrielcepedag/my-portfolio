package com.example.massycake.utils;

import com.example.massycake.entities.Empleado;
import com.example.massycake.repository.EmpleadoRepo;
import org.springframework.stereotype.Service;

@Service
public class LoadDefaultData {
    private EmpleadoRepo empleadoRepo;
    public LoadDefaultData(EmpleadoRepo empleadoRepo) {
        this.empleadoRepo = empleadoRepo;
    }

    public void createDefaultData(){
        createDefaultEmployees();
    }

    private void createDefaultEmployees() {
        if (empleadoRepo.countEmpleado() < 1){
            System.out.println("Creación del empleado en la base de datos");
            Empleado admin = new Empleado("000-0000000-0", "admin", "admin", "admin", "admin", "000-000-0000", "");
            admin.setAdmin(true);
            Empleado chef = new Empleado("000-0000000-1","chef", "chef", "chef", "chef", "000-000-0000", "");
            Empleado cashier = new Empleado("000-0000000-2","cashier", "cashier", "cashier", "cashier", "000-000-0000", "");
            empleadoRepo.save(admin);
            empleadoRepo.save(cashier);
            empleadoRepo.save(chef);
        }
    }
}
