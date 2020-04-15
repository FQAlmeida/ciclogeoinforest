package com.udesc.dcc.bdes.ciclogeoinforest.dados;

import com.udesc.dcc.bdes.ciclogeoinforest.negocio.AcessoUsuarios;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Bairro;
import com.udesc.dcc.bdes.ciclogeoinforest.persistencia.BairrosJpaController;
import com.udesc.dcc.bdes.ciclogeoinforest.persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Leandro Lisura
 */
public class AcessoBairros {
    
    private BairrosJpaController bjc = null;
    private EntityManagerFactory emf = null;
    
    public AcessoBairros(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    /*
     * getBJC: Cria e/ou retorna um controlador de entidade mapeada no banco.
     */
    public BairrosJpaController getBJC() {

        if (bjc == null) {
            if (emf == null) {
                try {
                    emf = Persistence.createEntityManagerFactory("PU");
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
            bjc = new BairrosJpaController(emf);
            emf.close();
        }
        return bjc;
    }
    
    /*
     * getBairros: retorna uma lista contendo todos os bairros cadastrados no banco.
     */
    public List<Bairro> getBairros(){        
    
        List<Bairro> lista = getBJC().findBairrosEntities();
        bjc.getEntityManager().close();
        return lista;
        
    }
    
    /*
     * findBairro: faz a busca de um bairro pelo seu id.
     */
    public Bairro findBairro(int idBairro){
        return getBJC().findBairros(idBairro);
    }
    
    /*
     * newBairro: insere um novo bairro no banco.
     */
    public boolean newBairro(String nome, int cid_cod) {
        return getBJC().create(nome, cid_cod);
    }
    
    /*
     * updateBairro: atualiza um bairro existente no banco.
     */
    public boolean updateBairro(Bairro u) {
        try {
            getBJC().edit(u);
            return true;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (Exception ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    /*
     * deleteBairro: remove um bairro do banco.
     */
    public boolean deleteBairro(int bai_cod) {
        Bairro u = new Bairro(bai_cod);
        try {
            try {
                getBJC().destroy(u.getBaiCod());
                return true;
            } catch (Exception ex) {
                Logger.getLogger(AcessoBairros.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        } catch (Exception ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
}
