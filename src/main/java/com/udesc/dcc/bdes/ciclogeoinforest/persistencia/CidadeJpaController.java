package com.udesc.dcc.bdes.ciclogeoinforest.persistencia;

import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Cidade;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Estado;
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
public class CidadeJpaController implements Serializable {

    public CidadeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean create(String nome, int est_cod) {
        try {
            EntityManager em = getEntityManager();
            em.getTransaction().begin();
            
            String query = "INSERT INTO cidade (cid_nome, est_cod) VALUES ('" + nome + "'," + est_cod + ")";
            
            em.createNativeQuery(query).executeUpdate();
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
    
    public void create(Cidade cidade) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estado estCod = cidade.getEstCod();
            if (estCod != null) {
                estCod = em.getReference(estCod.getClass(), estCod.getEstCod());
                cidade.setEstCod(estCod);
            }
            em.persist(cidade);
            if (estCod != null) {
                estCod.getCidadeList().add(cidade);
                estCod = em.merge(estCod);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCidade(cidade.getCidCod()) != null) {
                throw new PreexistingEntityException("Cidade " + cidade + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cidade cidade) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cidade persistentCidade = em.find(Cidade.class, cidade.getCidCod());
            Estado estCodOld = persistentCidade.getEstCod();
            Estado estCodNew = cidade.getEstCod();
            if (estCodNew != null) {
                estCodNew = em.getReference(estCodNew.getClass(), estCodNew.getEstCod());
                cidade.setEstCod(estCodNew);
            }
            cidade = em.merge(cidade);
            if (estCodOld != null && !estCodOld.equals(estCodNew)) {
                estCodOld.getCidadeList().remove(cidade);
                estCodOld = em.merge(estCodOld);
            }
            if (estCodNew != null && !estCodNew.equals(estCodOld)) {
                estCodNew.getCidadeList().add(cidade);
                estCodNew = em.merge(estCodNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cidade.getCidCod();
                if (findCidade(id) == null) {
                    throw new NonexistentEntityException("The cidade with id " + id + " no longer exists.");
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
            Cidade cidade;
            try {
                cidade = em.getReference(Cidade.class, id);
                cidade.getCidCod();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cidade with id " + id + " no longer exists.", enfe);
            }
            Estado estCod = cidade.getEstCod();
            if (estCod != null) {
                estCod.getCidadeList().remove(cidade);
                estCod = em.merge(estCod);
            }
            em.remove(cidade);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cidade> findCidadeEntities() {
        return findCidadeEntities(true, -1, -1);
    }

    public List<Cidade> findCidadeEntities(int maxResults, int firstResult) {
        return findCidadeEntities(false, maxResults, firstResult);
    }

    private List<Cidade> findCidadeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Cidade as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Cidade findCidade(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cidade.class, id);
        } finally {
            em.close();
        }
    }

    public int getCidadeCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Cidade as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
