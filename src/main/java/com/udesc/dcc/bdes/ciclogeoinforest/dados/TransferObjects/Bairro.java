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
@Table(name = "bairros")
@NamedQueries({
    @NamedQuery(name = "Bairro.findAll", query = "SELECT b FROM Bairro b"),
    @NamedQuery(name = "Bairro.findByBaiCod", query = "SELECT b FROM Bairro b WHERE b.baiCod = :baiCod"),
    @NamedQuery(name = "Bairro.findByBaiNome", query = "SELECT b FROM Bairro b WHERE b.baiNome = :baiNome"),
    @NamedQuery(name = "Bairro.findByCidCod", query = "SELECT b FROM Bairro b WHERE b.cidCod = :cidCod")})

public class Bairro implements Serializable {
    @JoinTable(name = "bairrotrilha", joinColumns = {
        @JoinColumn(name = "bai_cod", referencedColumnName = "bai_cod")}, inverseJoinColumns = {
        @JoinColumn(name = "codt", referencedColumnName = "codt")})
    @ManyToMany
    private List<Trilhadados> trilhadadosList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "bai_cod")
    private Integer baiCod;
    @Basic(optional = false)
    @Column(name = "bai_nome")
    private String baiNome;
    @Column(name = "cid_cod")
    private Integer cidCod;

    public Bairro(){
    }
    
    public Bairro(Integer baiCod) {
        this.baiCod = baiCod;
    }

    public Bairro(Integer baiCod, String baiNome, int cidCod) {
        this.baiCod = baiCod;
        this.baiNome = baiNome;
        this.cidCod = cidCod;
    }

    public Integer getBaiCod() {
        return baiCod;
    }

    public void setBaiCod(Integer baiCod) {
        this.baiCod = baiCod;
    }

    public String getBaiNome() {
        return baiNome;
    }

    public void setBaiNome(String baiNome) {
        this.baiNome = baiNome;
    }

    public Integer getCidCod() {
        return cidCod;
    }

    public void setCidCod(Integer cidCod) {
        this.cidCod = cidCod;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (baiCod != null ? baiCod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bairro)) {
            return false;
        }
        Bairro other = (Bairro) object;
        if ((this.baiCod == null && other.baiCod != null) || (this.baiCod != null && !this.baiCod.equals(other.baiCod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.udesc.joinville.dcc.bdes.ciclogeoinfo.persistencia.Bairros[ baiCod=" + baiCod + " ]";
    }

    public List<Trilhadados> getTrilhadadosList() {
        return trilhadadosList;
    }

    public void setTrilhadadosList(List<Trilhadados> trilhadadosList) {
        this.trilhadadosList = trilhadadosList;
    }
    
}
