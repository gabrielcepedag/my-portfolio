package servicios;

import encapsulaciones.Producto;
import encapsulaciones.Usuario;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class GestionDbUsuario extends MainGestionDb<Usuario>{
    private static GestionDbUsuario instance;
//    private static EntityManagerFactory emf;

//    public EntityManager getEntityManager() {
//        return emf.createEntityManager();
//    }

    public GestionDbUsuario() {
        super(Usuario.class);
    }

//    public GestionDbUsuario() {
//        if (emf == null){
//            emf = Persistence.createEntityManagerFactory("MiUnidadPersistencia");
//        }
//    }

    public static GestionDbUsuario getInstance() {
        if (instance == null)
            instance = new GestionDbUsuario();
        return instance;
    }


//    public Usuario crearUsuario(Usuario u1) throws IllegalArgumentException, EntityExistsException, PersistenceException {
//        EntityManager em = getEntityManager();
//
//        try {
//            em.getTransaction().begin();
//            em.persist(u1);
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//        return u1;
//    }

    public Usuario findUsuario(Object id) throws PersistenceException {
        EntityManager em = getEntityManager();

        try{
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public List<Usuario> findAll() throws PersistenceException {
        EntityManager em = getEntityManager();

        try{
            CriteriaQuery<Usuario> criteriaQuery = em.getCriteriaBuilder().createQuery(Usuario.class);
            criteriaQuery.select(criteriaQuery.from(Usuario.class));
            return em.createQuery(criteriaQuery).getResultList();
        } finally {
            em.close();
        }
    }

    public Usuario findUsuarioByUsername(String username) {
        EntityManager em = getEntityManager();
        Usuario u1 = null;

        try {
            Query query = em.createQuery("select u from Usuario u where u.username = :username");
            query.setParameter("username", username);
            List<Usuario> usuarios = query.getResultList();
            if (usuarios.size() > 0){
                u1 = usuarios.get(0);
            }
        } finally {
            em.close();
        }
        return u1;
    }
}
