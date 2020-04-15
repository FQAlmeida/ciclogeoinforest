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
@Table(name = "regiao")
@NamedQueries({
    @NamedQuery(name = "Regiao.findAll", query = "SELECT r FROM Regiao r"),
    @NamedQuery(name = "Regiao.findByRegCod", query = "SELECT r FROM Regiao r WHERE r.regCod = :regCod"),
    @NamedQuery(name = "Regiao.findByRegNome", query = "SELECT r FROM Regiao r WHERE r.regNome = :regNome")})
public class Regiao implements Serializable {
    @JoinTable(name = "regiaotrilha", joinColumns = {
        @JoinColumn(name = "reg_cod", referencedColumnName = "reg_cod")}, inverseJoinColumns = {
        @JoinColumn(name = "codt", referencedColumnName = "codt")})
    @ManyToMany
    private List<Trilhadados> trilhadadosList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "reg_cod")
    private Integer regCod;
    @Column(name = "reg_nome")
    private String regNome;

    public Regiao() {
    }

    public Regiao(Integer regCod) {
        this.regCod = regCod;
    }

    public Integer getRegCod() {
        return regCod;
    }

    public void setRegCod(Integer regCod) {
        this.regCod = regCod;
    }

    public String getRegNome() {
        return regNome;
    }

    public void setRegNome(String regNome) {
        this.regNome = regNome;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (regCod != null ? regCod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Regiao)) {
            return false;
        }
        Regiao other = (Regiao) object;
        if ((this.regCod == null && other.regCod != null) || (this.regCod != null && !this.regCod.equals(other.regCod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.udesc.joinville.dcc.bdes.ciclogeoinfo.persistencia.Regiao[ regCod=" + regCod + " ]";
    }

    public List<Trilhadados> getTrilhadadosList() {
        return trilhadadosList;
    }

    public void setTrilhadadosList(List<Trilhadados> trilhadadosList) {
        this.trilhadadosList = trilhadadosList;
    }
    
}
