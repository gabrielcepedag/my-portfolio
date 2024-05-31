package servicios;
import encapsulaciones.Url;
import encapsulaciones.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;

import java.util.List;

public class GestionDbUrl extends MainGestionDb<Url>{

    private static GestionDbUrl instance;
    public GestionDbUrl() {
        super(Url.class);
    }

    public static GestionDbUrl getInstance() {
        if (instance == null)
            instance = new GestionDbUrl();
        return instance;
    }

    public Url findOne(Object id) throws PersistenceException {
        EntityManager em = getEntityManager();
        try{
            return em.find(Url.class, id);
        } finally {
            em.close();
        }
    }

    public List<Url> findAll() throws PersistenceException {
        EntityManager em = getEntityManager();

        try{
            CriteriaQuery<Url> criteriaQuery = em.getCriteriaBuilder().createQuery(Url.class);
            criteriaQuery.select(criteriaQuery.from(Url.class));
            return em.createQuery(criteriaQuery).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Url> findAllUrlByUsuario(long idUsuario, int limit, int offset) throws PersistenceException {
        EntityManager em = getEntityManager();

        try{
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Url> criteriaQuery = criteriaBuilder.createQuery(Url.class);
            Root<Url> urlRoot = criteriaQuery.from(Url.class);
            Predicate userPredicate = criteriaBuilder.equal(urlRoot.get("usuario"), idUsuario);
            criteriaQuery.where(userPredicate);
            return em.createQuery(criteriaQuery).setFirstResult(offset).setMaxResults(limit).getResultList();
        } finally {
            em.close();
        }
    }

    public Long cantUserLink() {
        EntityManager em = getEntityManager();
        Long cantUserLink;

        try {
            Query query = em.createQuery("select count(DISTINCT(usuario)) from Url");
            cantUserLink = (Long) query.getSingleResult();
        } finally {
            em.close();
        }
        return cantUserLink;
    }


    public Long cantUrl() {
        EntityManager em = getEntityManager();
        Long cantUrl;

        try {
            Query query = em.createQuery("select count(*) from Url");
            cantUrl = (Long) query.getSingleResult();
        } finally {
            em.close();
        }
        return cantUrl;
    }
}
