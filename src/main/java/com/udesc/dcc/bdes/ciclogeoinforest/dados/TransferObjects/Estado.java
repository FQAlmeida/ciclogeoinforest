/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Leandro
 */
@Entity
@Table(name = "estado")
@NamedQueries({
    @NamedQuery(name = "Estado.findAll", query = "SELECT e FROM Estado e"),
    @NamedQuery(name = "Estado.findByEstCod", query = "SELECT e FROM Estado e WHERE e.estCod = :estCod"),
    @NamedQuery(name = "Estado.findByEstNome", query = "SELECT e FROM Estado e WHERE e.estNome = :estNome")})
public class Estado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "est_cod")
    private Integer estCod;
    @Basic(optional = false)
    @Column(name = "est_nome")
    private String estNome;
    @OneToMany(mappedBy = "estCod")
    private List<Cidade> cidadeList;

    public Estado() {
    }

    public Estado(Integer estCod) {
        this.estCod = estCod;
    }

    public Estado(Integer estCod, String estNome) {
        this.estCod = estCod;
        this.estNome = estNome;
    }

    public Integer getEstCod() {
        return estCod;
    }

    public void setEstCod(Integer estCod) {
        this.estCod = estCod;
    }

    public String getEstNome() {
        return estNome;
    }

    public void setEstNome(String estNome) {
        this.estNome = estNome;
    }

    public List<Cidade> getCidadeList() {
        return cidadeList;
    }

    public void setCidadeList(List<Cidade> cidadeList) {
        this.cidadeList = cidadeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estCod != null ? estCod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estado)) {
            return false;
        }
        Estado other = (Estado) object;
        if ((this.estCod == null && other.estCod != null) || (this.estCod != null && !this.estCod.equals(other.estCod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.udesc.joinville.dcc.bdes.ciclogeoinfo.persistencia.Estado[ estCod=" + estCod + " ]";
    }
    
}
