package com.udesc.dcc.bdes.ciclogeoinforest.persistencia;

import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Trilha;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Trilhadados;
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
public class TrilhaJpaController implements Serializable {

    public TrilhaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Trilha trilha) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Trilhadados codt = trilha.getCodt();
            if (codt != null) {
                codt = em.getReference(codt.getClass(), codt.getCodt());
                trilha.setCodt(codt);
            }
            em.persist(trilha);
            if (codt != null) {
                codt.getTrilhaList().add(trilha);
                codt = em.merge(codt);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTrilha(trilha.getCodtParte()) != null) {
                throw new PreexistingEntityException("Trilha " + trilha + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Trilha trilha) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Trilha persistentTrilha = em.find(Trilha.class, trilha.getCodtParte());
            Trilhadados codtOld = persistentTrilha.getCodt();
            Trilhadados codtNew = trilha.getCodt();
            if (codtNew != null) {
                codtNew = em.getReference(codtNew.getClass(), codtNew.getCodt());
                trilha.setCodt(codtNew);
            }
            trilha = em.merge(trilha);
            if (codtOld != null && !codtOld.equals(codtNew)) {
                codtOld.getTrilhaList().remove(trilha);
                codtOld = em.merge(codtOld);
            }
            if (codtNew != null && !codtNew.equals(codtOld)) {
                codtNew.getTrilhaList().add(trilha);
                codtNew = em.merge(codtNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = trilha.getCodtParte();
                if (findTrilha(id) == null) {
                    throw new NonexistentEntityException("The trilha with id " + id + " no longer exists.");
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
            Trilha trilha;
            try {
                trilha = em.getReference(Trilha.class, id);
                trilha.getCodtParte();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The trilha with id " + id + " no longer exists.", enfe);
            }
            Trilhadados codt = trilha.getCodt();
            if (codt != null) {
                codt.getTrilhaList().remove(trilha);
                codt = em.merge(codt);
            }
            em.remove(trilha);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Trilha> findTrilhaEntities() {
        return findTrilhaEntities(true, -1, -1);
    }

    public List<Trilha> findTrilhaEntities(int maxResults, int firstResult) {
        return findTrilhaEntities(false, maxResults, firstResult);
    }

    private List<Trilha> findTrilhaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Trilha as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Trilha findTrilha(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Trilha.class, id);
        } finally {
            em.close();
        }
    }

    public int getTrilhaCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Trilha as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
