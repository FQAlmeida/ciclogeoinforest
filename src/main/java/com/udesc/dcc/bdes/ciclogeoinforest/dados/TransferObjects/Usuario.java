/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Leandro
 */
@Entity
@Table(name = "usuario")
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByUsuCod", query = "SELECT u FROM Usuario u WHERE u.usuCod = :usuCod"),
    @NamedQuery(name = "Usuario.findByUsuLogin", query = "SELECT u FROM Usuario u WHERE u.usuEmail = :usuEmail"),
    @NamedQuery(name = "Usuario.findByUsuEmail", query = "SELECT u FROM Usuario u WHERE u.usuEmail = :usuEmail"),
    @NamedQuery(name = "Usuario.findByUsuNome", query = "SELECT u FROM Usuario u WHERE u.usuNome = :usuNome"),
    @NamedQuery(name = "Usuario.findByUsuSenha", query = "SELECT u FROM Usuario u WHERE u.usuSenha = :usuSenha")})
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "usu_cod")
    private Integer usuCod;
    @Basic(optional = false)
    @Column(name = "usu_email")
    private String usuEmail;
    @Basic(optional = false)
    @Column(name = "usu_nome")
    private String usuNome;
    @Basic(optional = false)
    @Column(name = "usu_senha")
    private String usuSenha;
    @Column(name = "deletado")
    private Integer deletado;

    public Usuario() {
    }

    public Usuario(Integer usuCod) {
        this.usuCod = usuCod;
    }

    public Usuario(Integer usuCod, String usuEmail, String usuNome, String usuSenha) {
        this.usuCod = usuCod;
        this.usuEmail = usuEmail;
        this.usuNome = usuNome;
        this.usuSenha = usuSenha;
        this.deletado = 0;
    }

    public Integer getUsuCod() {
        return usuCod;
    }

    public void setUsuCod(Integer usuCod) {
        this.usuCod = usuCod;
    }

    public String getUsuEmail() {
        return usuEmail;
    }

    public void setUsuEmail(String usuEmail) {
        this.usuEmail = usuEmail;
    }

    public String getUsuNome() {
        return usuNome;
    }

    public void setUsuNome(String usuNome) {
        this.usuNome = usuNome;
    }

    public String getUsuSenha() {
        return usuSenha;
    }

    public void setUsuSenha(String usuSenha) {
        this.usuSenha = usuSenha;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuCod != null ? usuCod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.usuCod == null && other.usuCod != null) || (this.usuCod != null && !this.usuCod.equals(other.usuCod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.udesc.joinville.dcc.bdes.ciclogeoinfo.persistencia.Usuario[ usuCod=" + usuCod + " ]";
    }

    /**
     * @return the deletado
     */
    public Integer getDeletado() {
        return deletado;
    }

    /**
     * @param deletado the deletado to set
     */
    public void setDeletado(Integer deletado) {
        this.deletado = deletado;
    }
    
}
