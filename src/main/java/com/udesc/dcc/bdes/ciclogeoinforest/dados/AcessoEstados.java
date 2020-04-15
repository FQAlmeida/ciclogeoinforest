package com.udesc.dcc.bdes.ciclogeoinforest.dados;

import com.udesc.dcc.bdes.ciclogeoinforest.negocio.AcessoUsuarios;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Estado;
import com.udesc.dcc.bdes.ciclogeoinforest.persistencia.EstadoJpaController;
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
public class AcessoEstados {
   
    private EstadoJpaController ejc = null;
    private EntityManagerFactory emf = null;
  
    public AcessoEstados(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    /*
     * getEJC: Cria e/ou retorna um controlador de entidade mapeada no banco.
     */
    private EstadoJpaController getEJC() {

        if (ejc == null) {
            if (emf == null) {
                try {
                    emf = Persistence.createEntityManagerFactory("PU");
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
            ejc = new EstadoJpaController(emf);
            emf.close();
        }
        return ejc;
    }
    
    /*
     * getEstados: retorna uma lista contendo todos os estados cadastrados no banco.
     */
    public List<Estado> getEstados(){
        
        List<Estado> lista = getEJC().findEstadoEntities();
        ejc.getEntityManager().close();
        return lista;
        
    }
    
    /*
     * newEstado: insere um novo estado no banco.
     */
    public void newEstado(Estado u) {
        try {
            getEJC().create(u);
        } catch (PreexistingEntityException ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
     * updateEstado: atualiza um estado existente no banco.
     */
    public void updateEstado(Estado u) {
        try {
            getEJC().edit(u);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
     * deleteEstado: remove um estado do banco.
     */
    public void deleteEstado(Estado u) {
        try {
            getEJC().destroy(u.getEstCod());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
