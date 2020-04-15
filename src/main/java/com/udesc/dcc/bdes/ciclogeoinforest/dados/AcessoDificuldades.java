package com.udesc.dcc.bdes.ciclogeoinforest.dados;

import com.udesc.dcc.bdes.ciclogeoinforest.negocio.AcessoUsuarios;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Dificuldade;
import com.udesc.dcc.bdes.ciclogeoinforest.persistencia.DificuldadeJpaController;
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
public class AcessoDificuldades {
    
    private DificuldadeJpaController djc = null;
    private EntityManagerFactory emf = null;
    
    public AcessoDificuldades(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    /*
     * getDJC: Cria e/ou retorna um controlador de entidade mapeada no banco.
     */
    private DificuldadeJpaController getDJC() {

        if (djc == null) {
            if (emf == null) {
                try {
                    emf = Persistence.createEntityManagerFactory("PU");
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
            djc = new DificuldadeJpaController(emf);
            emf.close();
        }
        return djc;
    }
    
    /*
     * getDificuldades: retorna uma lista contendo todas as cidades cadastradas no banco.
     */
    public List<Dificuldade> getDificuldades(){

        List<Dificuldade> lista = getDJC().findDificuldadeEntities();
        djc.getEntityManager().close();
        return lista;
        
    }
    
    /*
     * newDificuldade: Insere uma nova dificuldade no banco.
     */
    public void newDificuldade(Dificuldade u) {
        try {
            getDJC().create(u);
        } catch (PreexistingEntityException ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
     * updateDificuldade: atualiza uma dificuldade existente no banco.
     */
    public void updateDificuldade(Dificuldade u) {
        try {
            getDJC().edit(u);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
     * deleteDificuldade: remove uma dificuldade do banco.
     */
    public void deleteDificuldade(Dificuldade u) {
        try {
            getDJC().destroy(u.getDifCod());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
