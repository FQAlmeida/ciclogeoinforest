package com.udesc.dcc.bdes.ciclogeoinforest.persistencia;

import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Bairro;
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
public class BairrosJpaController implements Serializable {

    public BairrosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean create(String nome, int cid_cod) {
        try {
            EntityManager em = null;
            em = getEntityManager();
            em.getTransaction().begin();
            
            String query = "INSERT INTO bairros (bai_nome, cid_cod) VALUES ('" + nome + "'," + cid_cod + ")";
            
            em.createNativeQuery(query).executeUpdate();
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
    
    public void create(Bairro bairros) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(bairros);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBairros(bairros.getBaiCod()) != null) {
                throw new PreexistingEntityException("Bairros " + bairros + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bairro bairros) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            bairros = em.merge(bairros);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bairros.getBaiCod();
                if (findBairros(id) == null) {
                    throw new NonexistentEntityException("The bairros with id " + id + " no longer exists.");
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
            Bairro bairros;
            try {
                bairros = em.getReference(Bairro.class, id);
                bairros.getBaiCod();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bairros with id " + id + " no longer exists.", enfe);
            }
            em.remove(bairros);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bairro> findBairrosEntities() {
        return findBairrosEntities(true, -1, -1);
    }

    public List<Bairro> findBairrosEntities(int maxResults, int firstResult) {
        return findBairrosEntities(false, maxResults, firstResult);
    }

    private List<Bairro> findBairrosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Bairro as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Bairro findBairros(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bairro.class, id);
        } finally {
            em.close();
        }
    }

    public int getBairrosCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Bairro as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
