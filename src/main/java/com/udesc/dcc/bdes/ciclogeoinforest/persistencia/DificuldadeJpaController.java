package com.udesc.dcc.bdes.ciclogeoinforest.persistencia;

import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Dificuldade;
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
public class DificuldadeJpaController implements Serializable {

    public DificuldadeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Dificuldade dificuldade) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(dificuldade);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDificuldade(dificuldade.getDifCod()) != null) {
                throw new PreexistingEntityException("Dificuldade " + dificuldade + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Dificuldade dificuldade) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            dificuldade = em.merge(dificuldade);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = dificuldade.getDifCod();
                if (findDificuldade(id) == null) {
                    throw new NonexistentEntityException("The dificuldade with id " + id + " no longer exists.");
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
            Dificuldade dificuldade;
            try {
                dificuldade = em.getReference(Dificuldade.class, id);
                dificuldade.getDifCod();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dificuldade with id " + id + " no longer exists.", enfe);
            }
            em.remove(dificuldade);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Dificuldade> findDificuldadeEntities() {
        return findDificuldadeEntities(true, -1, -1);
    }

    public List<Dificuldade> findDificuldadeEntities(int maxResults, int firstResult) {
        return findDificuldadeEntities(false, maxResults, firstResult);
    }

    private List<Dificuldade> findDificuldadeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Dificuldade as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Dificuldade findDificuldade(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Dificuldade.class, id);
        } finally {
            em.close();
        }
    }

    public int getDificuldadeCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Dificuldade as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
