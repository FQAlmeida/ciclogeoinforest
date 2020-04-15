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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Leandro
 */
@Entity
@Table(name = "trilha")
@NamedQueries({
    @NamedQuery(name = "Trilha.findAll", query = "SELECT t FROM Trilha t"),
    @NamedQuery(name = "Trilha.findByCodtParte", query = "SELECT t FROM Trilha t WHERE t.codtParte = :codtParte")})
public class Trilha implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codt_parte")
    private Integer codtParte;
    @Lob
    @Column(name = "geometria")
    private Object geometria;
    @JoinColumn(name = "codt", referencedColumnName = "codt")
    @ManyToOne(optional = false)
    private Trilhadados codt;

    public Trilha() {
    }

    public Trilha(Integer codtParte) {
        this.codtParte = codtParte;
    }

    public Integer getCodtParte() {
        return codtParte;
    }

    public void setCodtParte(Integer codtParte) {
        this.codtParte = codtParte;
    }

    public Object getGeometria() {
        return geometria;
    }

    public void setGeometria(Object geometria) {
        this.geometria = geometria;
    }

    public Trilhadados getCodt() {
        return codt;
    }

    public void setCodt(Trilhadados codt) {
        this.codt = codt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codtParte != null ? codtParte.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trilha)) {
            return false;
        }
        Trilha other = (Trilha) object;
        if ((this.codtParte == null && other.codtParte != null) || (this.codtParte != null && !this.codtParte.equals(other.codtParte))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.udesc.joinville.dcc.bdes.ciclogeoinfo.persistencia.Trilha[ codtParte=" + codtParte + " ]";
    }
    
}
