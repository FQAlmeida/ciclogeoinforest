package com.udesc.dcc.bdes.ciclogeoinforest.persistencia;

import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Superficie;
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
public class SuperficieJpaController implements Serializable {

    public SuperficieJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Superficie superficie) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(superficie);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSuperficie(superficie.getSupCod()) != null) {
                throw new PreexistingEntityException("Superficie " + superficie + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Superficie superficie) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            superficie = em.merge(superficie);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = superficie.getSupCod();
                if (findSuperficie(id) == null) {
                    throw new NonexistentEntityException("The superficie with id " + id + " no longer exists.");
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
            Superficie superficie;
            try {
                superficie = em.getReference(Superficie.class, id);
                superficie.getSupCod();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The superficie with id " + id + " no longer exists.", enfe);
            }
            em.remove(superficie);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Superficie> findSuperficieEntities() {
        return findSuperficieEntities(true, -1, -1);
    }

    public List<Superficie> findSuperficieEntities(int maxResults, int firstResult) {
        return findSuperficieEntities(false, maxResults, firstResult);
    }

    private List<Superficie> findSuperficieEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Superficie as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Superficie findSuperficie(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Superficie.class, id);
        } finally {
            em.close();
        }
    }

    public int getSuperficieCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Superficie as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
