package servicios;

import encapsulaciones.Ventas;

import encapsulaciones.Usuario;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class GestionDbVenta extends MainGestionDb<Ventas>{

    private static GestionDbVenta instance;
//    private static EntityManagerFactory emf;

//    public EntityManager getEntityManager() {
//        return emf.createEntityManager();
//    }

    private GestionDbVenta() {
        super(Ventas.class);
    }

    //    public GestionDbVenta() {
//        if (emf == null) {
//            emf = Persistence.createEntityManagerFactory("MiUnidadPersistencia");
//        }
//    }
    public static GestionDbVenta getInstance() {
        if (instance == null)
            instance = new GestionDbVenta();
        return instance;
    }

//
//    public Ventas crearVenta(Ventas venta) throws IllegalArgumentException, EntityExistsException, PersistenceException {
//        EntityManager em = getEntityManager();
//
//        try {
//            em.getTransaction().begin();
//            em.persist(venta);
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//        return venta;
//    }
    public Ventas buscarVenta(Object id) throws PersistenceException {
        EntityManager em = getEntityManager();

        try{
            return em.find(Ventas.class, id);
        } finally {
            em.close();
        }
    }
    public List<Ventas> findAll() throws PersistenceException {
        EntityManager em = getEntityManager();

        try{
            CriteriaQuery<Ventas> criteriaQuery = em.getCriteriaBuilder().createQuery(Ventas.class);
            criteriaQuery.select(criteriaQuery.from(Ventas.class));
            return em.createQuery(criteriaQuery).getResultList();
        } finally {
            em.close();
        }
    }

    public Ventas buscarVentaByCodigo(String codigo) {
        EntityManager em = getEntityManager();
        Ventas v1 = null;

        try {
            Query query = em.createQuery("select v from Ventas v where v.codigo = :codigo");
            query.setParameter("codigo", codigo);
            List<Ventas> ventas = query.getResultList();
            if (ventas.size() > 0){
                v1 = ventas.get(0);
            }
        } finally {
            em.close();
        }
        return v1;
    }
}
