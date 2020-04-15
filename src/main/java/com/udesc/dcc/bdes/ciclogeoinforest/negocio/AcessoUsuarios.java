package com.udesc.dcc.bdes.ciclogeoinforest.negocio;

import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Usuario;
import com.udesc.dcc.bdes.ciclogeoinforest.persistencia.UsuarioJpaController;
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
public class AcessoUsuarios {

    private UsuarioJpaController ujc = null;
    private EntityManagerFactory emf = null;

    public AcessoUsuarios(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /*
     * getUJC: Cria e/ou retorna um controlador de entidade mapeada no banco.
     */
    private UsuarioJpaController getUJC() {

        if (ujc == null) {
            if (emf == null) {
                try {
                    emf = Persistence.createEntityManagerFactory("PU");
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            ujc = new UsuarioJpaController(emf);
            emf.close();
        }
        return ujc;

    }

    /*
     * verificaLogin: recebe um login e uma senha, verifica se o usuário existe e sua senha está correta.
     */
    public boolean verificaLogin(String login, String senha) {

        Usuario u;
        if (!getUJC().getEntityManager().createNamedQuery("Usuario.findByUsuEmail").setParameter("usuEmail", login).getResultList().isEmpty()) {
            u = (Usuario) getUJC().getEntityManager().createNamedQuery("Usuario.findByUsuEmail").setParameter("usuEmail", login).getSingleResult();
            if (u.getUsuSenha().equals(senha) && u.getDeletado() != 1) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    /*
     * getUsuCodByLogin: retorna o código do usuário com o login passado por parametro.
     */
    public int getUsuCodByLogin(String login) {

        if (!getUJC().getEntityManager().createNamedQuery("Usuario.findByUsuLogin").setParameter("usuEmail", login).getResultList().isEmpty()) {
            Usuario usu;
            usu = (Usuario) getUJC().getEntityManager().createNamedQuery("Usuario.findByUsuLogin").setParameter("usuEmail", login).getSingleResult();
            return usu.getUsuCod();
        } else {
            return -1;
        }

    }
    
        /*
     * getUsuByLogin: retorna o usuário com o login passado por parametro.
     */
    public Usuario getUsuByLogin(String login) {

        if (!getUJC().getEntityManager().createNamedQuery("Usuario.findByUsuEmail").setParameter("usuEmail", login).getResultList().isEmpty()) {
            Usuario usu;
            usu = (Usuario) getUJC().getEntityManager().createNamedQuery("Usuario.findByUsuEmail").setParameter("usuEmail", login).getSingleResult();
            return usu;
        } else {
            return null;
        }

    }
    
     /*
     * existeEmail: checa se email existe.
     */
    public boolean existeEmail(String email) {

        if (!getUJC().getEntityManager().createNamedQuery("Usuario.findByUsuEmail").setParameter("usuEmail", email).getResultList().isEmpty())
            return true;
            
            
        return false;
    }

    /*
     * newUsuario: faz a inserção de um novo usuário no banco.
     */
    public boolean newUsuario(String email, String nome, String senha) {
        return getUJC().create(email, nome, senha);
    }

    /*
     * updateUsuario: atualiza um usuário existente no banco.
     */
    public boolean updateUsuario(Usuario u, String oldLogin, String oldSenha) {

        if (getUsuCodByLogin(u.getUsuEmail()) == -1) {
            return false;
        } else {
            try {
                getUJC().edit(u);
                return true;
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            } catch (Exception ex) {
                Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }

    }

    /*
     * deleteUsuario: remove um usuário do banco.
     */
    public boolean deleteUsuario(Usuario u) {

        try {
            getUJC().destroy(u);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(AcessoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    /*
     * getUsuarios: retorna uma lista contendo todos os usuários cadastrados no banco.
     */
    public List<Usuario> getUsuarios() {

        List<Usuario> lista = getUJC().findusuarioEntities();
        ujc.getEntityManager().close();
        return lista;

    }
}
