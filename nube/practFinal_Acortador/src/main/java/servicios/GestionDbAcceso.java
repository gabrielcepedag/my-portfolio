package servicios;

import encapsulaciones.Acceso;
import encapsulaciones.Url;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class GestionDbAcceso extends MainGestionDb<Acceso>{

    private static GestionDbAcceso instance;

    public GestionDbAcceso() {
        super(Acceso.class);
    }

    public static GestionDbAcceso getInstance() {
        if (instance == null)
            instance = new GestionDbAcceso();
        return instance;
    }

    public Acceso findOne(Object id) throws PersistenceException {
        EntityManager em = getEntityManager();
        try{
            return em.find(Acceso.class, id);
        } finally {
            em.close();
        }
    }

    public List<Acceso> findAll() throws PersistenceException {
        EntityManager em = getEntityManager();

        try{
            CriteriaQuery<Acceso> criteriaQuery = em.getCriteriaBuilder().createQuery(Acceso.class);
            criteriaQuery.select(criteriaQuery.from(Acceso.class));
            return em.createQuery(criteriaQuery).getResultList();
        } finally {
            em.close();
        }
    }

    public Long getTotalVisits() {
        EntityManager em = getEntityManager();
        Long cantAcceso;

        try {
            Query query = em.createQuery("select count(id) from Acceso");
            cantAcceso = (Long) query.getSingleResult();
        } finally {
            em.close();
        }
        return cantAcceso;
    }

    public List<Acceso> findAllByUser(long idUsuario) {
        EntityManager em = getEntityManager();
        List<Acceso> accesos = new ArrayList<>();

        try {
            Query query = em.createQuery("select a from Acceso a where a.url.usuario.id = :idUsuario");
            query.setParameter("idUsuario", idUsuario);
            accesos = query.getResultList();
        } finally {
            em.close();
        }
        return accesos;
    }

    public List<Acceso> findAllByUrl(long idUrl) {
        EntityManager em = getEntityManager();
        List<Acceso> accesos = new ArrayList<>();

        try {
            Query query = em.createQuery("select a from Acceso a where a.url.id = :idUrl");
            query.setParameter("idUrl", idUrl);
            accesos = query.getResultList();
        } finally {
            em.close();
        }
        return accesos;
    }
}
