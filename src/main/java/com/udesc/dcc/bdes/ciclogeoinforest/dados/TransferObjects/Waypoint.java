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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Leandro
 */
@Entity
@Table(name = "waypoint")
@NamedQueries({
    @NamedQuery(name = "Waypoint.findAll", query = "SELECT w FROM Waypoint w"),
    @NamedQuery(name = "Waypoint.findByCodwp", query = "SELECT w FROM Waypoint w WHERE w.codwp = :codwp"),
    @NamedQuery(name = "Waypoint.findByDescricao", query = "SELECT w FROM Waypoint w WHERE w.descricao = :descricao"),
    @NamedQuery(name = "Waypoint.findByNome", query = "SELECT w FROM Waypoint w WHERE w.nome = :nome")})
public class Waypoint implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codwp")
    private Integer codwp;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "usu_cod")
    private Integer usuCod;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Lob
    @Column(name = "geometria")
    private Object geometria;
    @ManyToMany(mappedBy = "waypointList")
    private List<Categoria> categoriasList;
    @JoinColumn(name = "codt", referencedColumnName = "codt")
    @ManyToOne(optional = false)
    private Trilhadados codt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "waypoint")
    private List<Imagem> imagemList;

    public Waypoint() {
    }

    public Waypoint(Integer codwp) {
        this.codwp = codwp;
    }

    public Waypoint(Integer codwp, String nome) {
        this.codwp = codwp;
        this.nome = nome;
    }

    public Integer getCodwp() {
        return codwp;
    }

    public void setCodwp(Integer codwp) {
        this.codwp = codwp;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Object getGeometria() {
        return geometria;
    }

    public void setGeometria(Object geometria) {
        this.geometria = geometria;
    }

    public List<Categoria> getCategoriasList() {
        return categoriasList;
    }

    public void setCategoriasList(List<Categoria> categoriasList) {
        this.categoriasList = categoriasList;
    }

    public Trilhadados getCodt() {
        return codt;
    }

    public void setCodt(Trilhadados codt) {
        this.codt = codt;
    }

    public List<Imagem> getImagemList() {
        return imagemList;
    }

    public void setImagemList(List<Imagem> imagemList) {
        this.imagemList = imagemList;
    }
    
    public Integer getUsuCod() {
        return usuCod;
    }

    public void setUsuCod(Integer usuCod) {
        this.usuCod = usuCod;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codwp != null ? codwp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Waypoint)) {
            return false;
        }
        Waypoint other = (Waypoint) object;
        if ((this.codwp == null && other.codwp != null) || (this.codwp != null && !this.codwp.equals(other.codwp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.udesc.joinville.dcc.bdes.ciclogeoinfo.persistencia.Waypoint[ codwp=" + codwp + " ]";
    }
    
}
