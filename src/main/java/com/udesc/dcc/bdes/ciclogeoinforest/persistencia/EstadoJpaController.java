package com.udesc.dcc.bdes.ciclogeoinforest.persistencia;

import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Cidade;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Estado;
import com.udesc.dcc.bdes.ciclogeoinforest.persistencia.exceptions.NonexistentEntityException;
import com.udesc.dcc.bdes.ciclogeoinforest.persistencia.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Leandro
 */
public class EstadoJpaController implements Serializable {

    public EstadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estado estado) throws PreexistingEntityException, Exception {
        if (estado.getCidadeList() == null) {
            estado.setCidadeList(new ArrayList<Cidade>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Cidade> attachedCidadeList = new ArrayList<Cidade>();
            for (Cidade cidadeListCidadeToAttach : estado.getCidadeList()) {
                cidadeListCidadeToAttach = em.getReference(cidadeListCidadeToAttach.getClass(), cidadeListCidadeToAttach.getCidCod());
                attachedCidadeList.add(cidadeListCidadeToAttach);
            }
            estado.setCidadeList(attachedCidadeList);
            em.persist(estado);
            for (Cidade cidadeListCidade : estado.getCidadeList()) {
                Estado oldEstCodOfCidadeListCidade = cidadeListCidade.getEstCod();
                cidadeListCidade.setEstCod(estado);
                cidadeListCidade = em.merge(cidadeListCidade);
                if (oldEstCodOfCidadeListCidade != null) {
                    oldEstCodOfCidadeListCidade.getCidadeList().remove(cidadeListCidade);
                    oldEstCodOfCidadeListCidade = em.merge(oldEstCodOfCidadeListCidade);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstado(estado.getEstCod()) != null) {
                throw new PreexistingEntityException("Estado " + estado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estado estado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estado persistentEstado = em.find(Estado.class, estado.getEstCod());
            List<Cidade> cidadeListOld = persistentEstado.getCidadeList();
            List<Cidade> cidadeListNew = estado.getCidadeList();
            List<Cidade> attachedCidadeListNew = new ArrayList<Cidade>();
            for (Cidade cidadeListNewCidadeToAttach : cidadeListNew) {
                cidadeListNewCidadeToAttach = em.getReference(cidadeListNewCidadeToAttach.getClass(), cidadeListNewCidadeToAttach.getCidCod());
                attachedCidadeListNew.add(cidadeListNewCidadeToAttach);
            }
            cidadeListNew = attachedCidadeListNew;
            estado.setCidadeList(cidadeListNew);
            estado = em.merge(estado);
            for (Cidade cidadeListOldCidade : cidadeListOld) {
                if (!cidadeListNew.contains(cidadeListOldCidade)) {
                    cidadeListOldCidade.setEstCod(null);
                    cidadeListOldCidade = em.merge(cidadeListOldCidade);
                }
            }
            for (Cidade cidadeListNewCidade : cidadeListNew) {
                if (!cidadeListOld.contains(cidadeListNewCidade)) {
                    Estado oldEstCodOfCidadeListNewCidade = cidadeListNewCidade.getEstCod();
                    cidadeListNewCidade.setEstCod(estado);
                    cidadeListNewCidade = em.merge(cidadeListNewCidade);
                    if (oldEstCodOfCidadeListNewCidade != null && !oldEstCodOfCidadeListNewCidade.equals(estado)) {
                        oldEstCodOfCidadeListNewCidade.getCidadeList().remove(cidadeListNewCidade);
                        oldEstCodOfCidadeListNewCidade = em.merge(oldEstCodOfCidadeListNewCidade);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estado.getEstCod();
                if (findEstado(id) == null) {
                    throw new NonexistentEntityException("The estado with id " + id + " no longer exists.");
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
            Estado estado;
            try {
                estado = em.getReference(Estado.class, id);
                estado.getEstCod();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estado with id " + id + " no longer exists.", enfe);
            }
            List<Cidade> cidadeList = estado.getCidadeList();
            for (Cidade cidadeListCidade : cidadeList) {
                cidadeListCidade.setEstCod(null);
                cidadeListCidade = em.merge(cidadeListCidade);
            }
            em.remove(estado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estado> findEstadoEntities() {
        return findEstadoEntities(true, -1, -1);
    }

    public List<Estado> findEstadoEntities(int maxResults, int firstResult) {
        return findEstadoEntities(false, maxResults, firstResult);
    }

    private List<Estado> findEstadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Estado as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Estado findEstado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Estado as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
