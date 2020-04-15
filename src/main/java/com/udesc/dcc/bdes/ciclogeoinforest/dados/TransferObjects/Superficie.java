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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Leandro
 */
@Entity
@Table(name = "superficie")
@NamedQueries({
    @NamedQuery(name = "Superficie.findAll", query = "SELECT s FROM Superficie s"),
    @NamedQuery(name = "Superficie.findBySupCod", query = "SELECT s FROM Superficie s WHERE s.supCod = :supCod"),
    @NamedQuery(name = "Superficie.findBySupNome", query = "SELECT s FROM Superficie s WHERE s.supNome = :supNome"),
    @NamedQuery(name = "Superficie.findBySupComprimento", query = "SELECT s FROM Superficie s WHERE s.supComprimento = :supComprimento")})
public class Superficie implements Serializable {
    @JoinTable(name = "superficietrilha", joinColumns = {
        @JoinColumn(name = "sup_cod", referencedColumnName = "sup_cod")}, inverseJoinColumns = {
        @JoinColumn(name = "codt", referencedColumnName = "codt")})
    @ManyToMany
    private List<Trilhadados> trilhadadosList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "sup_cod")
    private Integer supCod;
    @Column(name = "sup_nome")
    private String supNome;
    @Column(name = "sup_comprimento")
    private Integer supComprimento;

    public Superficie() {
    }

    public Superficie(Integer supCod) {
        this.supCod = supCod;
    }

    public Integer getSupCod() {
        return supCod;
    }

    public void setSupCod(Integer supCod) {
        this.supCod = supCod;
    }

    public String getSupNome() {
        return supNome;
    }

    public void setSupNome(String supNome) {
        this.supNome = supNome;
    }

    public Integer getSupComprimento() {
        return supComprimento;
    }

    public void setSupComprimento(Integer supComprimento) {
        this.supComprimento = supComprimento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (supCod != null ? supCod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Superficie)) {
            return false;
        }
        Superficie other = (Superficie) object;
        if ((this.supCod == null && other.supCod != null) || (this.supCod != null && !this.supCod.equals(other.supCod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.udesc.joinville.dcc.bdes.ciclogeoinfo.persistencia.Superficie[ supCod=" + supCod + " ]";
    }

    public List<Trilhadados> getTrilhadadosList() {
        return trilhadadosList;
    }

    public void setTrilhadadosList(List<Trilhadados> trilhadadosList) {
        this.trilhadadosList = trilhadadosList;
    }
    
}
