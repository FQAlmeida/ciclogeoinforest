package com.udesc.dcc.bdes.ciclogeoinforest.dados;

import com.udesc.dcc.bdes.ciclogeoinforest.negocio.AcessoUsuarios;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Tipo;
import com.udesc.dcc.bdes.ciclogeoinforest.persistencia.TipoJpaController;
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
public class AcessoTipos {
    
    private TipoJpaController tjc = null;
    private EntityManagerFactory emf = null;
    
    public AcessoTipos(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    /*
     * getTJC: Cria e/ou retorna um controlador de entidade mapeada no banco.
     */
    private TipoJpaController getTJC() {

        if (tjc == null) {
            if (emf == null) {
                try {
                    emf = Persistence.createEntityManagerFactory("PU");
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
            tjc = new TipoJpaController(emf);
            emf.close();
        }
        return tjc;
    }
    
    /*
     * getTipos: retorna uma lista contendo todos os tipos cadastrados no banco.
     */
    public List<Tipo> getTipos(){
        
        List<Tipo> lista = getTJC().findTipoEntities();
        tjc.getEntityManager().close();
        return lista;
        
    }
    
    /*
     * newTipo: insere um novo tipo no banco.
     */
    public void newTipo(Tipo u) {
        try {
            getTJC().create(u);
        } catch (PreexistingEntityException ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
     * updateTipo: atualiza um tipo existente no banco.
     */
    public void updateTipo(Tipo u) {
        try {
            getTJC().edit(u);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
     * deleteTipo: remove um tipo do banco.
     */
    public void deleteTipo(Tipo u) {
        try {
            getTJC().destroy(u.getTipCod());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
