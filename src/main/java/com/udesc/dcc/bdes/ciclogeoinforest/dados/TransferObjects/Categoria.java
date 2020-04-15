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
@Table(name = "categorias")
@NamedQueries({
    @NamedQuery(name = "Categoria.findAll", query = "SELECT c FROM Categoria c"),
    @NamedQuery(name = "Categoria.findByCatCod", query = "SELECT c FROM Categoria c WHERE c.catCod = :catCod"),
    @NamedQuery(name = "Categoria.findByCatNome", query = "SELECT c FROM Categoria c WHERE c.catNome = :catNome")})
public class Categoria implements Serializable {
    @JoinTable(name = "categoriawaypoint", joinColumns = {
        @JoinColumn(name = "cat_cod", referencedColumnName = "cat_cod")}, inverseJoinColumns = {
        @JoinColumn(name = "codwp", referencedColumnName = "codwp")})
    @ManyToMany
    private List<Waypoint> waypointList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cat_cod")
    private Integer catCod;
    @Column(name = "cat_nome")
    private String catNome;

    public Categoria() {
    }

    public Categoria(Integer catCod) {
        this.catCod = catCod;
    }

    public Integer getCatCod() {
        return catCod;
    }

    public void setCatCod(Integer catCod) {
        this.catCod = catCod;
    }

    public String getCatNome() {
        return catNome;
    }

    public void setCatNome(String catNome) {
        this.catNome = catNome;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (catCod != null ? catCod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categoria)) {
            return false;
        }
        Categoria other = (Categoria) object;
        if ((this.catCod == null && other.catCod != null) || (this.catCod != null && !this.catCod.equals(other.catCod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.udesc.joinville.dcc.bdes.ciclogeoinfo.persistencia.Categorias[ catCod=" + catCod + " ]";
    }

    public List<Waypoint> getWaypointList() {
        return waypointList;
    }

    public void setWaypointList(List<Waypoint> waypointList) {
        this.waypointList = waypointList;
    }
    
}
