package com.udesc.dcc.bdes.ciclogeoinforest.persistencia;

import com.udesc.dcc.bdes.ciclogeoinforest.persistencia.exceptions.NonexistentEntityException;
import com.udesc.dcc.bdes.ciclogeoinforest.persistencia.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author Leandro
 */
public class GeometryColumnsJpaController implements Serializable {

    public GeometryColumnsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GeometryColumns geometryColumns) throws PreexistingEntityException, Exception {
        if (geometryColumns.getGeometryColumnsPK() == null) {
            geometryColumns.setGeometryColumnsPK(new GeometryColumnsPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(geometryColumns);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGeometryColumns(geometryColumns.getGeometryColumnsPK()) != null) {
                throw new PreexistingEntityException("GeometryColumns " + geometryColumns + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GeometryColumns geometryColumns) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            geometryColumns = em.merge(geometryColumns);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                GeometryColumnsPK id = geometryColumns.getGeometryColumnsPK();
                if (findGeometryColumns(id) == null) {
                    throw new NonexistentEntityException("The geometryColumns with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(GeometryColumnsPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GeometryColumns geometryColumns;
            try {
                geometryColumns = em.getReference(GeometryColumns.class, id);
                geometryColumns.getGeometryColumnsPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The geometryColumns with id " + id + " no longer exists.", enfe);
            }
            em.remove(geometryColumns);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GeometryColumns> findGeometryColumnsEntities() {
        return findGeometryColumnsEntities(true, -1, -1);
    }

    public List<GeometryColumns> findGeometryColumnsEntities(int maxResults, int firstResult) {
        return findGeometryColumnsEntities(false, maxResults, firstResult);
    }

    private List<GeometryColumns> findGeometryColumnsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from GeometryColumns as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public GeometryColumns findGeometryColumns(GeometryColumnsPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GeometryColumns.class, id);
        } finally {
            em.close();
        }
    }

    public int getGeometryColumnsCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from GeometryColumns as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
