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
@Table(name = "dificuldade")
@NamedQueries({
    @NamedQuery(name = "Dificuldade.findAll", query = "SELECT d FROM Dificuldade d"),
    @NamedQuery(name = "Dificuldade.findByDifCod", query = "SELECT d FROM Dificuldade d WHERE d.difCod = :difCod"),
    @NamedQuery(name = "Dificuldade.findByDifNome", query = "SELECT d FROM Dificuldade d WHERE d.difNome = :difNome")})
public class Dificuldade implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "difCod")
    private List<Trilhadados> trilhadadosList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "dif_cod")
    private Integer difCod;
    @Column(name = "dif_nome")
    private String difNome;

    public Dificuldade() {
    }

    public Dificuldade(Integer difCod) {
        this.difCod = difCod;
    }

    public Integer getDifCod() {
        return difCod;
    }

    public void setDifCod(Integer difCod) {
        this.difCod = difCod;
    }

    public String getDifNome() {
        return difNome;
    }

    public void setDifNome(String difNome) {
        this.difNome = difNome;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (difCod != null ? difCod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dificuldade)) {
            return false;
        }
        Dificuldade other = (Dificuldade) object;
        if ((this.difCod == null && other.difCod != null) || (this.difCod != null && !this.difCod.equals(other.difCod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.udesc.joinville.dcc.bdes.ciclogeoinfo.persistencia.Dificuldade[ difCod=" + difCod + " ]";
    }

    public List<Trilhadados> getTrilhadadosList() {
        return trilhadadosList;
    }

    public void setTrilhadadosList(List<Trilhadados> trilhadadosList) {
        this.trilhadadosList = trilhadadosList;
    }
    
}
