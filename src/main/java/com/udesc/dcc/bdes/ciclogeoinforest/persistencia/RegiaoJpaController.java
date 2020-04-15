package com.udesc.dcc.bdes.ciclogeoinforest.persistencia;

import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Regiao;
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
public class RegiaoJpaController implements Serializable {

    public RegiaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Regiao regiao) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(regiao);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRegiao(regiao.getRegCod()) != null) {
                throw new PreexistingEntityException("Regiao " + regiao + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Regiao regiao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            regiao = em.merge(regiao);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = regiao.getRegCod();
                if (findRegiao(id) == null) {
                    throw new NonexistentEntityException("The regiao with id " + id + " no longer exists.");
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
            Regiao regiao;
            try {
                regiao = em.getReference(Regiao.class, id);
                regiao.getRegCod();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The regiao with id " + id + " no longer exists.", enfe);
            }
            em.remove(regiao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Regiao> findRegiaoEntities() {
        return findRegiaoEntities(true, -1, -1);
    }

    public List<Regiao> findRegiaoEntities(int maxResults, int firstResult) {
        return findRegiaoEntities(false, maxResults, firstResult);
    }

    private List<Regiao> findRegiaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Regiao as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Regiao findRegiao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Regiao.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegiaoCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Regiao as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
