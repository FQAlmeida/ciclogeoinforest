package com.udesc.dcc.bdes.ciclogeoinforest.dados;

import com.udesc.dcc.bdes.ciclogeoinforest.negocio.AcessoUsuarios;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Cidade;
import com.udesc.dcc.bdes.ciclogeoinforest.persistencia.CidadeJpaController;
import com.udesc.dcc.bdes.ciclogeoinforest.persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Leandro
 */
public class AcessoCidades {
 
    private CidadeJpaController cjc = null;
    private EntityManagerFactory emf = null;
    
    public AcessoCidades(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    /*
     * getCJC: Cria e/ou retorna um controlador de entidade mapeada no banco.
     */
    private CidadeJpaController getCJC() {

        if (cjc == null) {
            if (emf == null) {
                try {
                    emf = Persistence.createEntityManagerFactory("PU");
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
            cjc = new CidadeJpaController(emf);
            emf.close();
        }
        return cjc;
    }
    
    /*
     * getCidades: retorna uma lista contendo todas as cidades cadastradas no banco.
     */
    public List<Cidade> getCidades(){
        
        List<Cidade> lista = getCJC().findCidadeEntities();
        cjc.getEntityManager().close();
        return lista;
        
    }
    
    /*
     * findCidade: faz a busca de uma cidade pelo seu id.
     */
    public Cidade findCidade(int idCidade){
        return getCJC().findCidade(idCidade);
    }
    
    /*
     * newCidade: insere uma nova cidade no banco
     */
    public boolean newCidade(String nome, int est_cod) {
        return getCJC().create(nome, est_cod);
    }
    
    /*
     * updateCidade: atualiza uma cidade existente no banco
     */
    public boolean updateCidade(Cidade u) {
        try {
            getCJC().edit(u);
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
     * deleteCidade: remove uma cidade do banco.
     */
    public boolean deleteCidade(int cid_cod) {
        Cidade u = new Cidade(cid_cod);
        try {
            getCJC().destroy(u.getCidCod());
            return true;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
}
