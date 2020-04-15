/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "tipo")
@NamedQueries({
    @NamedQuery(name = "Tipo.findAll", query = "SELECT t FROM Tipo t"),
    @NamedQuery(name = "Tipo.findByTipCod", query = "SELECT t FROM Tipo t WHERE t.tipCod = :tipCod"),
    @NamedQuery(name = "Tipo.findByTipNome", query = "SELECT t FROM Tipo t WHERE t.tipNome = :tipNome")})
public class Tipo implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipCod")
    private List<Trilhadados> trilhadadosList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "tip_cod")
    private Integer tipCod;
    @Column(name = "tip_nome")
    private String tipNome;

    public Tipo() {
    }

    public Tipo(Integer tipCod) {
        this.tipCod = tipCod;
    }

    public Integer getTipCod() {
        return tipCod;
    }

    public void setTipCod(Integer tipCod) {
        this.tipCod = tipCod;
    }

    public String getTipNome() {
        return tipNome;
    }

    public void setTipNome(String tipNome) {
        this.tipNome = tipNome;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipCod != null ? tipCod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipo)) {
            return false;
        }
        Tipo other = (Tipo) object;
        if ((this.tipCod == null && other.tipCod != null) || (this.tipCod != null && !this.tipCod.equals(other.tipCod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.udesc.joinville.dcc.bdes.ciclogeoinfo.persistencia.Tipo[ tipCod=" + tipCod + " ]";
    }

    public List<Trilhadados> getTrilhadadosList() {
        return trilhadadosList;
    }

    public void setTrilhadadosList(List<Trilhadados> trilhadadosList) {
        this.trilhadadosList = trilhadadosList;
    }
    
}
