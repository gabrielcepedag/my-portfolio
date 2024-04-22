package servicios;

import encapsulaciones.Acceso;
import encapsulaciones.Url;
import encapsulaciones.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class GestionDbUsuario extends MainGestionDb<Usuario>{
    private static GestionDbUsuario instance;
    public GestionDbUsuario() {
        super(Usuario.class);
    }
    public static GestionDbUsuario getInstance() {
        if (instance == null)
            instance = new GestionDbUsuario();
        return instance;
    }
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

    public Long contUsuario() {
        EntityManager em = getEntityManager();

        try{
            CriteriaQuery<Long> criteriaQuery = em.getCriteriaBuilder().createQuery(Long.class);
            Root<Usuario> root = criteriaQuery.from(Usuario.class);
            criteriaQuery.select(em.getCriteriaBuilder().count(root));
            return em.createQuery(criteriaQuery).getSingleResult();
        } finally {
            em.close();
        }
    }

    public Usuario findUsuarioByUsername(String username) {
        EntityManager em = getEntityManager();
        List<Usuario> usuarios = new ArrayList<>();
        Usuario u1 = null;

        try {
            Query query = em.createQuery("select u from Usuario u where u.username = :username");
            query.setParameter("username", username);
//            u1 = (Usuario) query.getSingleResult();
            usuarios = query.getResultList();

            if (usuarios.size() > 0){
                u1 = usuarios.get(0);
            }
        } finally {
            em.close();
        }
        return u1;
    }

    public void addAcceso(Acceso acceso) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(acceso);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void removeAcceso(Acceso acceso) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(acceso);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    public Long cantAdminUser() {
        EntityManager em = getEntityManager();

        try{
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            Root<Usuario> usuarioRoot = criteriaQuery.from(Usuario.class);
            criteriaQuery.select(em.getCriteriaBuilder().count(usuarioRoot));
            Predicate userPredicate = criteriaBuilder.equal(usuarioRoot.get("admin"), "true");
            criteriaQuery.where(userPredicate);
            return em.createQuery(criteriaQuery).getSingleResult();
        } finally {
            em.close();
        }
    }
}
