package com.udesc.dcc.bdes.ciclogeoinforest.persistencia;

import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Usuario;
import com.udesc.dcc.bdes.ciclogeoinforest.persistencia.exceptions.NonexistentEntityException;
import com.udesc.dcc.bdes.ciclogeoinforest.persistencia.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 *
 * @author Leandro
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /*
     * Método create(String email, String nome, String senha);
     * Método sobrescrito, motivo:
     * o outro create envia a coluna usu_cod instanciada,
     * o que impossibilita o auto-incremento para geração desse campo.
     */
    
    public boolean create(String email, String nome, String senha) {
        try {
            EntityManager em = getEntityManager();
            em.getTransaction().begin();

            String query = "INSERT INTO usuario (usu_email, usu_nome, usu_senha, deletado) VALUES ('" + email + "','" + nome + "','" + senha + "', 0)";
            
            em.createNativeQuery(query).executeUpdate();
            em.getTransaction().commit();
            em.close();
            
            return true;
            
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findusuario(usuario.getUsuCod()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            usuario = em.merge(usuario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getUsuCod();
                if (findusuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /*
     * Método destroy(Usuario u);
     * Método sobrescrito, não remove do banco:
     * apenas seta valor 1 em deletado para simular remoção do banco.
     */
    
    public boolean destroy(Usuario u) throws NonexistentEntityException {
        try {
            EntityManager em = getEntityManager();
            em.getTransaction().begin();

            String query = "UPDATE usuario SET deletado = 1 WHERE usu_cod = " + u.getUsuCod();
            
            em.createNativeQuery(query).executeUpdate();
            em.getTransaction().commit();
            em.close();
            
            return true;
            
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    
    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getUsuCod();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findusuarioEntities() {
        return findusuarioEntities(true, -1, -1);
    }

    public List<Usuario> findusuarioEntities(int maxResults, int firstResult) {
        return findusuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findusuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Usuario as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Usuario findusuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            if(em.find(Usuario.class, id) != null)
                return em.find(Usuario.class, id);
            else
                return new Usuario(-1, "", "", "");
        } finally {
            em.close();
        }
    }

    public int getusuarioCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Usuario as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
