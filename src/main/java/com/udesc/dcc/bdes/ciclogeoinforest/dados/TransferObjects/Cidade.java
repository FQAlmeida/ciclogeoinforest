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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Leandro
 */
@Entity
@Table(name = "cidade")
@NamedQueries({
    @NamedQuery(name = "Cidade.findAll", query = "SELECT c FROM Cidade c"),
    @NamedQuery(name = "Cidade.findByCidCod", query = "SELECT c FROM Cidade c WHERE c.cidCod = :cidCod"),
    @NamedQuery(name = "Cidade.findByCidNome", query = "SELECT c FROM Cidade c WHERE c.cidNome = :cidNome")})
public class Cidade implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cid_cod")
    private Integer cidCod;
    @Column(name = "cid_nome")
    private String cidNome;
    @JoinColumn(name = "est_cod", referencedColumnName = "est_cod")
    @ManyToOne
    private Estado estCod;

    public Cidade() {
    }

    public Cidade(Integer cidCod) {
        this.cidCod = cidCod;
    }

    public Cidade(Integer cidCod, String cidNome, Estado estCod) {
        this.cidCod = cidCod;
        this.cidNome = cidNome;
        this.estCod = estCod;
    }

    public Integer getCidCod() {
        return cidCod;
    }

    public void setCidCod(Integer cidCod) {
        this.cidCod = cidCod;
    }

    public String getCidNome() {
        return cidNome;
    }

    public void setCidNome(String cidNome) {
        this.cidNome = cidNome;
    }

    public Estado getEstCod() {
        return estCod;
    }

    public void setEstCod(Estado estCod) {
        this.estCod = estCod;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cidCod != null ? cidCod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cidade)) {
            return false;
        }
        Cidade other = (Cidade) object;
        if ((this.cidCod == null && other.cidCod != null) || (this.cidCod != null && !this.cidCod.equals(other.cidCod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.udesc.joinville.dcc.bdes.ciclogeoinfo.persistencia.Cidade[ cidCod=" + cidCod + " ]";
    }
}
