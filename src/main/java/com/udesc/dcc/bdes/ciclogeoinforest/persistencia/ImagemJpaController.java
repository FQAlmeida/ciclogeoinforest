package com.udesc.dcc.bdes.ciclogeoinforest.persistencia;

import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Waypoint;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Imagem;
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
public class ImagemJpaController implements Serializable {

    public ImagemJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Imagem imagem) throws PreexistingEntityException, Exception {
        if (imagem.getImagemPK() == null) {
            imagem.setImagemPK(new ImagemPK());
        }
        imagem.getImagemPK().setCodwp(imagem.getWaypoint().getCodwp());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Waypoint waypoint = imagem.getWaypoint();
            if (waypoint != null) {
                waypoint = em.getReference(waypoint.getClass(), waypoint.getCodwp());
                imagem.setWaypoint(waypoint);
            }
            em.persist(imagem);
            if (waypoint != null) {
                waypoint.getImagemList().add(imagem);
                waypoint = em.merge(waypoint);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findImagem(imagem.getImagemPK()) != null) {
                throw new PreexistingEntityException("Imagem " + imagem + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Imagem imagem) throws NonexistentEntityException, Exception {
        imagem.getImagemPK().setCodwp(imagem.getWaypoint().getCodwp());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Imagem persistentImagem = em.find(Imagem.class, imagem.getImagemPK());
            Waypoint waypointOld = persistentImagem.getWaypoint();
            Waypoint waypointNew = imagem.getWaypoint();
            if (waypointNew != null) {
                waypointNew = em.getReference(waypointNew.getClass(), waypointNew.getCodwp());
                imagem.setWaypoint(waypointNew);
            }
            imagem = em.merge(imagem);
            if (waypointOld != null && !waypointOld.equals(waypointNew)) {
                waypointOld.getImagemList().remove(imagem);
                waypointOld = em.merge(waypointOld);
            }
            if (waypointNew != null && !waypointNew.equals(waypointOld)) {
                waypointNew.getImagemList().add(imagem);
                waypointNew = em.merge(waypointNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ImagemPK id = imagem.getImagemPK();
                if (findImagem(id) == null) {
                    throw new NonexistentEntityException("The imagem with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ImagemPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Imagem imagem;
            try {
                imagem = em.getReference(Imagem.class, id);
                imagem.getImagemPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The imagem with id " + id + " no longer exists.", enfe);
            }
            Waypoint waypoint = imagem.getWaypoint();
            if (waypoint != null) {
                waypoint.getImagemList().remove(imagem);
                waypoint = em.merge(waypoint);
            }
            em.remove(imagem);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Imagem> findImagemEntities() {
        return findImagemEntities(true, -1, -1);
    }

    public List<Imagem> findImagemEntities(int maxResults, int firstResult) {
        return findImagemEntities(false, maxResults, firstResult);
    }

    private List<Imagem> findImagemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Imagem as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Imagem findImagem(ImagemPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Imagem.class, id);
        } finally {
            em.close();
        }
    }

    public int getImagemCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Imagem as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
