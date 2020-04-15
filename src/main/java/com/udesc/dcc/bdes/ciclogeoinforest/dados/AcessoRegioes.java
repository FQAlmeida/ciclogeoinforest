package com.udesc.dcc.bdes.ciclogeoinforest.dados;

import com.udesc.dcc.bdes.ciclogeoinforest.negocio.AcessoUsuarios;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Regiao;
import com.udesc.dcc.bdes.ciclogeoinforest.persistencia.RegiaoJpaController;
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
public class AcessoRegioes {
    
    private RegiaoJpaController rjc = null;
    private EntityManagerFactory emf = null;
    
    public AcessoRegioes(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    /*
     * getRJC: Cria e/ou retorna um controlador de entidade mapeada no banco.
     */
    private RegiaoJpaController getRJC() {

        if (rjc == null) {
            if (emf == null) {
                try {
                    emf = Persistence.createEntityManagerFactory("PU");
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
            rjc = new RegiaoJpaController(emf);
            emf.close();
        }
        return rjc;
    }
    
    /*
     * getRegioes: retorna uma lista contendo todas as regiões cadastradas no banco.
     */
    public List<Regiao> getRegioes(){
        
        List<Regiao> lista = getRJC().findRegiaoEntities();
        rjc.getEntityManager().close();
        return lista;
        
    }
    
    /*
     * newRegiao: insere uma nova região no banco.
     */
    public void newRegiao(Regiao u) {
        try {
            getRJC().create(u);
        } catch (PreexistingEntityException ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
     * updateRegiao: atualiza uma região existente no banco.
     */
    public void updateRegiao(Regiao u) {
        try {
            getRJC().edit(u);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
     * deleteRegiao: remove uma região do banco.
     */
    public void deleteRegiao(Regiao u) {
        try {
            getRJC().destroy(u.getRegCod());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
