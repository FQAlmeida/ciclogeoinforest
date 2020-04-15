package com.udesc.dcc.bdes.ciclogeoinforest.persistencia;

import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Waypoint;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Categoria;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Imagem;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Trilhadados;
import com.udesc.dcc.bdes.ciclogeoinforest.persistencia.exceptions.IllegalOrphanException;
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
public class WaypointJpaController implements Serializable {

    public WaypointJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /* inserirWaypoint:
     * Insere os dados dos waypoints no banco. Se der erro, desfaz o que foi feito
     * Variáveis:
     *  int verif = para verificar o sucesso da inserção
     * Retornos:
     *  false = deu algum erro, todas as alterações foram desfeitas
     *  true = deu tudo certo */
    public boolean create(int codt, String descricao, String nome, String geometria, int usu_cod,
            byte[] imagem, ArrayList<Integer> categorias) {

        int verif;
        EntityManager em = null;

        try {

            //Inserir waypoints
            Imagem i = new Imagem();
            String[] url_imagem;
            String SQL1 = "INSERT INTO waypoint (codt,descricao, nome, geometria, usu_cod) VALUES ("
                    + codt + "," 
                    + "'" + descricao + "', "
                    + "'" + nome + "', "
                    + "ST_GeomFromText('POINT(" + geometria + ")',4326), "
                    + usu_cod + ") RETURNING codwp";

            em = getEntityManager();
            em.getTransaction().begin();

            Object o = em.createNativeQuery(SQL1).getSingleResult();
            int codwp = Integer.parseInt(o.toString());

            //Inserir imagens
            if(imagem!=null){
                String SQL2 = "INSERT INTO imagem (codwp, codimg, bytes) VALUES ("
                        + codwp + ", 1, ?1)";
                verif = em.createNativeQuery(SQL2).setParameter(1, imagem).executeUpdate();

                if (verif != 1) { //Se não retornar 1 significa que não inseriu ou deu algum outro erro
                    em.getTransaction().commit();
                    em.close();
                    return false;
                }
            }

            //Inserir categorias
            for (int j = 0; j < categorias.size(); j++) {

                String SQL3 = "INSERT INTO categoriawaypoint VALUES ("
                        + codwp + ", "
                        + categorias.get(j) + ")";
System.out.println("Query categoriawaypoint= "+SQL3);
                verif = em.createNativeQuery(SQL3).executeUpdate();

                if (verif != 1) { //Se não retornar 1 significa que não inseriu ou deu algum outro erro
                    em.getTransaction().commit();
                    em.close();
                    return false;
                }
            }

            em.getTransaction().commit();
            em.close();

        } catch (Exception e) {
            System.out.println(e);
            em.getTransaction().commit();
            em.close();
            return false;
        }

        return true;

    }

    public void create(Waypoint waypoint) throws PreexistingEntityException, Exception {
        if (waypoint.getCategoriasList() == null) {
            waypoint.setCategoriasList(new ArrayList<Categoria>());
        }
        if (waypoint.getImagemList() == null) {
            waypoint.setImagemList(new ArrayList<Imagem>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Trilhadados codt = waypoint.getCodt();
            if (codt != null) {
                codt = em.getReference(codt.getClass(), codt.getCodt());
                waypoint.setCodt(codt);
            }
            List<Categoria> attachedCategoriasList = new ArrayList<Categoria>();
            for (Categoria categoriasListCategoriasToAttach : waypoint.getCategoriasList()) {
                categoriasListCategoriasToAttach = em.getReference(categoriasListCategoriasToAttach.getClass(), categoriasListCategoriasToAttach.getCatCod());
                attachedCategoriasList.add(categoriasListCategoriasToAttach);
            }
            waypoint.setCategoriasList(attachedCategoriasList);
            List<Imagem> attachedImagemList = new ArrayList<Imagem>();
            for (Imagem imagemListImagemToAttach : waypoint.getImagemList()) {
                imagemListImagemToAttach = em.getReference(imagemListImagemToAttach.getClass(), imagemListImagemToAttach.getImagemPK());
                attachedImagemList.add(imagemListImagemToAttach);
            }
            waypoint.setImagemList(attachedImagemList);
            em.persist(waypoint);
            if (codt != null) {
                codt.getWaypointList().add(waypoint);
                codt = em.merge(codt);
            }
            for (Categoria categoriasListCategorias : waypoint.getCategoriasList()) {
                categoriasListCategorias.getWaypointList().add(waypoint);
                categoriasListCategorias = em.merge(categoriasListCategorias);
            }
            for (Imagem imagemListImagem : waypoint.getImagemList()) {
                Waypoint oldWaypointOfImagemListImagem = imagemListImagem.getWaypoint();
                imagemListImagem.setWaypoint(waypoint);
                imagemListImagem = em.merge(imagemListImagem);
                if (oldWaypointOfImagemListImagem != null) {
                    oldWaypointOfImagemListImagem.getImagemList().remove(imagemListImagem);
                    oldWaypointOfImagemListImagem = em.merge(oldWaypointOfImagemListImagem);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findWaypoint(waypoint.getCodwp()) != null) {
                throw new PreexistingEntityException("Waypoint " + waypoint + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Waypoint waypoint) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Waypoint persistentWaypoint = em.find(Waypoint.class, waypoint.getCodwp());
            Trilhadados codtOld = persistentWaypoint.getCodt();
            Trilhadados codtNew = waypoint.getCodt();
            List<Categoria> categoriasListOld = persistentWaypoint.getCategoriasList();
            List<Categoria> categoriasListNew = waypoint.getCategoriasList();
            List<Imagem> imagemListOld = persistentWaypoint.getImagemList();
            List<Imagem> imagemListNew = waypoint.getImagemList();
            List<String> illegalOrphanMessages = null;
            for (Imagem imagemListOldImagem : imagemListOld) {
                if (!imagemListNew.contains(imagemListOldImagem)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Imagem " + imagemListOldImagem + " since its waypoint field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codtNew != null) {
                codtNew = em.getReference(codtNew.getClass(), codtNew.getCodt());
                waypoint.setCodt(codtNew);
            }
            List<Categoria> attachedCategoriasListNew = new ArrayList<Categoria>();
            for (Categoria categoriasListNewCategoriasToAttach : categoriasListNew) {
                categoriasListNewCategoriasToAttach = em.getReference(categoriasListNewCategoriasToAttach.getClass(), categoriasListNewCategoriasToAttach.getCatCod());
                attachedCategoriasListNew.add(categoriasListNewCategoriasToAttach);
            }
            categoriasListNew = attachedCategoriasListNew;
            waypoint.setCategoriasList(categoriasListNew);
            List<Imagem> attachedImagemListNew = new ArrayList<Imagem>();
            for (Imagem imagemListNewImagemToAttach : imagemListNew) {
                imagemListNewImagemToAttach = em.getReference(imagemListNewImagemToAttach.getClass(), imagemListNewImagemToAttach.getImagemPK());
                attachedImagemListNew.add(imagemListNewImagemToAttach);
            }
            imagemListNew = attachedImagemListNew;
            waypoint.setImagemList(imagemListNew);
            waypoint = em.merge(waypoint);
            if (codtOld != null && !codtOld.equals(codtNew)) {
                codtOld.getWaypointList().remove(waypoint);
                codtOld = em.merge(codtOld);
            }
            if (codtNew != null && !codtNew.equals(codtOld)) {
                codtNew.getWaypointList().add(waypoint);
                codtNew = em.merge(codtNew);
            }
            for (Categoria categoriasListOldCategorias : categoriasListOld) {
                if (!categoriasListNew.contains(categoriasListOldCategorias)) {
                    categoriasListOldCategorias.getWaypointList().remove(waypoint);
                    categoriasListOldCategorias = em.merge(categoriasListOldCategorias);
                }
            }
            for (Categoria categoriasListNewCategorias : categoriasListNew) {
                if (!categoriasListOld.contains(categoriasListNewCategorias)) {
                    categoriasListNewCategorias.getWaypointList().add(waypoint);
                    categoriasListNewCategorias = em.merge(categoriasListNewCategorias);
                }
            }
            for (Imagem imagemListNewImagem : imagemListNew) {
                if (!imagemListOld.contains(imagemListNewImagem)) {
                    Waypoint oldWaypointOfImagemListNewImagem = imagemListNewImagem.getWaypoint();
                    imagemListNewImagem.setWaypoint(waypoint);
                    imagemListNewImagem = em.merge(imagemListNewImagem);
                    if (oldWaypointOfImagemListNewImagem != null && !oldWaypointOfImagemListNewImagem.equals(waypoint)) {
                        oldWaypointOfImagemListNewImagem.getImagemList().remove(imagemListNewImagem);
                        oldWaypointOfImagemListNewImagem = em.merge(oldWaypointOfImagemListNewImagem);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = waypoint.getCodwp();
                if (findWaypoint(id) == null) {
                    throw new NonexistentEntityException("The waypoint with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public boolean delete(int codwp) {

        EntityManager em = null;
        em = getEntityManager();
        em.getTransaction().begin();

        int success = em.createNativeQuery("DELETE FROM waypoint WHERE codwp = " + codwp).executeUpdate();

        em.getTransaction().commit();
        em.close();

        if (success == 1) {
            return true;
        } else {
            return false;
        }

    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Waypoint waypoint;
            try {
                waypoint = em.getReference(Waypoint.class, id);
                waypoint.getCodwp();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The waypoint with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Imagem> imagemListOrphanCheck = waypoint.getImagemList();
            for (Imagem imagemListOrphanCheckImagem : imagemListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Waypoint (" + waypoint + ") cannot be destroyed since the Imagem " + imagemListOrphanCheckImagem + " in its imagemList field has a non-nullable waypoint field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Trilhadados codt = waypoint.getCodt();
            if (codt != null) {
                codt.getWaypointList().remove(waypoint);
                codt = em.merge(codt);
            }
            List<Categoria> categoriasList = waypoint.getCategoriasList();
            for (Categoria categoriasListCategorias : categoriasList) {
                categoriasListCategorias.getWaypointList().remove(waypoint);
                categoriasListCategorias = em.merge(categoriasListCategorias);
            }
            em.remove(waypoint);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Waypoint> findWaypointEntities() {
        return findWaypointEntities(true, -1, -1);
    }

    public List<Waypoint> findWaypointEntities(int maxResults, int firstResult) {
        return findWaypointEntities(false, maxResults, firstResult);
    }

    private List<Waypoint> findWaypointEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Waypoint as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Waypoint findWaypoint(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Waypoint.class, id);
        } finally {
            em.close();
        }
    }

    public int getWaypointCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Waypoint as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
