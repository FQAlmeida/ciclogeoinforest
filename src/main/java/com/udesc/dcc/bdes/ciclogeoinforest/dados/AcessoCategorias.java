package com.udesc.dcc.bdes.ciclogeoinforest.dados;

import com.udesc.dcc.bdes.ciclogeoinforest.negocio.AcessoUsuarios;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Categoria;
import com.udesc.dcc.bdes.ciclogeoinforest.persistencia.CategoriasJpaController;
import com.udesc.dcc.bdes.ciclogeoinforest.persistencia.exceptions.NonexistentEntityException;
import com.udesc.dcc.bdes.ciclogeoinforest.persistencia.exceptions.PreexistingEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Leandro
 */
public class AcessoCategorias {
    
    private CategoriasJpaController cjc = null;
    private EntityManagerFactory emf = null;
    
    public AcessoCategorias(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    /*
     * getCJC: Cria e/ou retorna um controlador de entidade mapeada no banco.
     */
    private CategoriasJpaController getCJC() {

        if (cjc == null) {
            if (emf == null) {
                try {
                    emf = Persistence.createEntityManagerFactory("PU");
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
            cjc = new CategoriasJpaController(emf);
            emf.close();
        }
        return cjc;
    }
    
    /*
     * getCategorias: retorna uma lista contendo todas as categorias cadastrados no banco.
     */
    public List<Categoria> getCategorias(){
        
        List<Categoria> lista = getCJC().findCategoriasEntities();
        cjc.getEntityManager().close();
        return lista;
        
    }
    
    /*
     * newCategoria: insere uma nova categoria no banco.
     */
    public void newCategoria(Categoria u) {
        try {
            getCJC().create(u);
        } catch (PreexistingEntityException ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
     * updateCategoria: atualiza uma categoria existente no banco.
     */
    public void updateCategoria(Categoria u) {
        try {
            getCJC().edit(u);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
     * deleteCategoria: remove uma categoria do banco.
     */
    public void deleteCategoria(Categoria u) {
        try {
            getCJC().destroy(u.getCatCod());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
