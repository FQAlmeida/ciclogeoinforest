package com.udesc.dcc.bdes.ciclogeoinforest.dados;

import com.udesc.dcc.bdes.ciclogeoinforest.negocio.AcessoUsuarios;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Superficie;
import com.udesc.dcc.bdes.ciclogeoinforest.persistencia.SuperficieJpaController;
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
public class AcessoSuperficies {

    private SuperficieJpaController sjc = null;
    private EntityManagerFactory emf = null;

    public AcessoSuperficies(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    /*
     * getSJC: Cria e/ou retorna um controlador de entidade mapeada no banco.
     */
    private SuperficieJpaController getSJC() {

        if (sjc == null) {
            if (emf == null) {
                try {
                    emf = Persistence.createEntityManagerFactory("PU");
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
            sjc = new SuperficieJpaController(emf);
            emf.close();
        }
        return sjc;
    }

    /*
     * getSuperficies: retorna uma lista contendo todas as superfícies cadastradas no banco.
     */
    public List<Superficie> getSuperficies() {

        List<Superficie> lista = getSJC().findSuperficieEntities();
        sjc.getEntityManager().close();
        return lista;

    }
    
    /*
     * newSuperficie: insere uma nova superfície no banco.
     */
    public void newSuperficie(Superficie u) {
        try {
            getSJC().create(u);
        } catch (PreexistingEntityException ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
     * updateSuperficie: atualiza uma superfície existente no banco.
     */
    public void updateSuperficie(Superficie u) {
        try {
            getSJC().edit(u);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
     * deleteSuperficie: remove uma superfície do banco.
     */
    public void deleteSuperficie(Superficie u) {
        try {
            getSJC().destroy(u.getSupCod());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
