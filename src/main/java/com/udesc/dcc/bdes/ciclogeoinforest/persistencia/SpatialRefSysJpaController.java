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
public class SpatialRefSysJpaController implements Serializable {

    public SpatialRefSysJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SpatialRefSys spatialRefSys) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(spatialRefSys);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSpatialRefSys(spatialRefSys.getSrid()) != null) {
                throw new PreexistingEntityException("SpatialRefSys " + spatialRefSys + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SpatialRefSys spatialRefSys) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            spatialRefSys = em.merge(spatialRefSys);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = spatialRefSys.getSrid();
                if (findSpatialRefSys(id) == null) {
                    throw new NonexistentEntityException("The spatialRefSys with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SpatialRefSys spatialRefSys;
            try {
                spatialRefSys = em.getReference(SpatialRefSys.class, id);
                spatialRefSys.getSrid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The spatialRefSys with id " + id + " no longer exists.", enfe);
            }
            em.remove(spatialRefSys);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SpatialRefSys> findSpatialRefSysEntities() {
        return findSpatialRefSysEntities(true, -1, -1);
    }

    public List<SpatialRefSys> findSpatialRefSysEntities(int maxResults, int firstResult) {
        return findSpatialRefSysEntities(false, maxResults, firstResult);
    }

    private List<SpatialRefSys> findSpatialRefSysEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from SpatialRefSys as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public SpatialRefSys findSpatialRefSys(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SpatialRefSys.class, id);
        } finally {
            em.close();
        }
    }

    public int getSpatialRefSysCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from SpatialRefSys as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
