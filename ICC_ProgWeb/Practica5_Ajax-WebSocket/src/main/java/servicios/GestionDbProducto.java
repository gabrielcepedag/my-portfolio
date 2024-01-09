package servicios;

import encapsulaciones.Comentario;
import encapsulaciones.Foto;
import encapsulaciones.Producto;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaQuery;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestionDbProducto extends MainGestionDb<Producto>{
    private static GestionDbProducto instance;

    private GestionDbProducto() {
        super(Producto.class);
    }

    public static GestionDbProducto getInstance() {
        if (instance == null)
            instance = new GestionDbProducto();
        return instance;
    }

    public Producto buscarProducto(Object id) throws PersistenceException {
        EntityManager em = getEntityManager();
        Producto p1 = null;
        try{
            p1 = em.find(Producto.class, id);
        } finally {
            em.close();
        }
        return p1;
    }
    public List<Producto> findAll() throws PersistenceException {
        EntityManager em = getEntityManager();

        try{
            CriteriaQuery<Producto> criteriaQuery = em.getCriteriaBuilder().createQuery(Producto.class);
            criteriaQuery.select(criteriaQuery.from(Producto.class));
            return em.createQuery(criteriaQuery).getResultList();
        } finally {
            em.close();
        }
    }
    public Long countProducto() throws SQLException {
        EntityManager em = getEntityManager();

        Query query = em.createQuery("select count (p.id) from Producto p");
        return (Long) query.getSingleResult();
    }
    public List<Producto> findAll(int limit, int offset) throws PersistenceException {
        EntityManager em = getEntityManager();

        try{
            CriteriaQuery<Producto> criteriaQuery = em.getCriteriaBuilder().createQuery(Producto.class);
            criteriaQuery.select(criteriaQuery.from(Producto.class));
            if(limit == -1){
                return em.createQuery(criteriaQuery).getResultList();
            }else{
                return em.createQuery(criteriaQuery).setFirstResult(offset).setMaxResults(limit).getResultList();
            }
        } finally {
            em.close();
        }
    }

    public Producto findByCodigo(int codigo) {
        EntityManager em = getEntityManager();
        Producto p1 = null;
        try {
            Query query = em.createQuery("select p from Producto p where p.codigo = :codigo");
            query.setParameter("codigo", codigo);
            List<Producto> productos = query.getResultList();
            if (productos.size() > 0){
                p1 = productos.get(0);
            }
        } finally {
            em.close();
        }
        return p1;
    }
    public List<Foto> findAllFotosByProd(Producto p1) {
        EntityManager em = getEntityManager();
        List<Foto> fotos = new ArrayList<>();

        try {
            Query query = em.createQuery("select f from Foto f where f.producto.codigo = :codigo");
            query.setParameter("codigo", p1.getCodigo());
            fotos = query.getResultList();
        } finally {
            em.close();
        }
        return fotos;
    }
    public Foto findFotoByCodigo(Long id) {
        EntityManager em = getEntityManager();
        Foto foto = null;

        try {
            Query query = em.createQuery("select f from Foto f where f.id = :id");
            query.setParameter("id", id);
            List<Foto> fotos = query.getResultList();
            if (fotos.size() > 0){
                foto = fotos.get(0);
            }
        } finally {
            em.close();
        }
        return foto;
    }
    public void removeFoto(Long idProd, Long idFoto) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();

        try {
            Query query = em.createQuery("delete from Foto f where f.id = :idFoto AND f.producto.id = :idProd");
            query.setParameter("idFoto", idFoto);
            query.setParameter("idProd", idProd);
            query.executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    public void addComentario(Comentario comentario) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(comentario);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    public void removeComentario(Producto producto, Long idComentario) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        List<Comentario> comentarios = producto.getComentarios();

        for (Comentario cmt : comentarios) {
            if (cmt.getId() == idComentario) {
                Comentario comentario = entityManager.find(Comentario.class, idComentario);
                if (comentario != null) {
                    entityManager.remove(entityManager.merge(comentario));
                }
                break;
            }
        }
        entityManager.merge(producto);
        entityManager.getTransaction().commit();
    }
    public Comentario getComentarioByCodigo(Long id) {
        EntityManager em = getEntityManager();
        Comentario comentario = null;

        try {
            Query query = em.createQuery("select p from Comentario p where p.id = :id");
            query.setParameter("id", id);
            List<Comentario> comentarios = query.getResultList();
            if (comentarios.size() > 0){
                comentario = comentarios.get(0);
            }
        } finally {
            em.close();
        }
        return comentario;
    }
}
