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
@Table(name = "trilhadados")
@NamedQueries({
    @NamedQuery(name = "Trilhadados.findAll", query = "SELECT t FROM Trilhadados t"),
    @NamedQuery(name = "Trilhadados.findByCodt", query = "SELECT t FROM Trilhadados t WHERE t.codt = :codt"),
    @NamedQuery(name = "Trilhadados.findByComprimentoKm", query = "SELECT t FROM Trilhadados t WHERE t.comprimentoKm = :comprimentoKm"),
    @NamedQuery(name = "Trilhadados.findByDesnivel", query = "SELECT t FROM Trilhadados t WHERE t.desnivel = :desnivel"),
    @NamedQuery(name = "Trilhadados.findByNome", query = "SELECT t FROM Trilhadados t WHERE t.nome = :nome"),
    @NamedQuery(name = "Trilhadados.findByDescricao", query = "SELECT t FROM Trilhadados t WHERE t.descricao = :descricao"),
   // @NamedQuery(name = "Trilhadados.findByArquivoGps", query = "SELECT t FROM Trilhadados t WHERE t.arquivoGps = :arquivoGps"),
    @NamedQuery(name = "Trilhadados.findByImgAltitude", query = "SELECT t FROM Trilhadados t WHERE t.imgAltitude = :imgAltitude"),
    @NamedQuery(name = "Trilhadados.findByCidCod", query = "SELECT t FROM Trilhadados t WHERE t.cidCod = :cidCod")})
public class Trilhadados implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codt")
    private Integer codt;
    @Basic(optional = false)
    @Column(name = "comprimento_km")
    private double comprimentoKm;
    @Basic(optional = false)
    @Column(name = "desnivel")
    private double desnivel;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Column(name = "descricao")
    private String descricao;
    @Basic(optional = false)
    @Column(name = "img_altitude")
    private Long imgAltitude;
    @Column(name = "cid_cod")
    private Integer cidCod;
    @ManyToMany(mappedBy = "trilhadadosList")
    private List<Regiao> regiaoList;
    @ManyToMany(mappedBy = "trilhadadosList")
    private List<Bairro> bairrosList;
    @ManyToMany(mappedBy = "trilhadadosList")
    private List<Superficie> superficieList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codt")
    private List<Trilha> trilhaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codt")
    private List<Waypoint> waypointList;
    @JoinColumn(name = "tip_cod", referencedColumnName = "tip_cod")
    @ManyToOne(optional = false)
    private Tipo tipCod;
    @JoinColumn(name = "dif_cod", referencedColumnName = "dif_cod")
    @ManyToOne(optional = false)
    private Dificuldade difCod;
    @Column(name = "imgba")
    private byte[] imgba;

    public Trilhadados() {
    }

    public Trilhadados(Integer codt) {
        this.codt = codt;
    }

    public Trilhadados(Integer codt, double comprimentoKm, double desnivel, String nome, long arquivoGps, long imgAltitude) {
        this.codt = codt;
        this.comprimentoKm = comprimentoKm;
        this.desnivel = desnivel;
        this.nome = nome;
        this.imgAltitude = imgAltitude;
    }

    public Integer getCodt() {
        return codt;
    }

    public void setCodt(Integer codt) {
        this.codt = codt;
    }

    public double getComprimentoKm() {
        return comprimentoKm;
    }

    public void setComprimentoKm(double comprimentoKm) {
        this.comprimentoKm = comprimentoKm;
    }

    public double getDesnivel() {
        return desnivel;
    }

    public void setDesnivel(double desnivel) {
        this.desnivel = desnivel;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

/*    public Long getArquivoGps() {
        return arquivoGps;
    }

    public void setArquivoGps(Long arquivoGps) {
        this.arquivoGps = arquivoGps;
    }*/

    public Long getImgAltitude() {
        return imgAltitude;
    }

    public void setImgAltitude(Long imgAltitude) {
        this.imgAltitude = imgAltitude;
    }

    public Integer getCidCod() {
        return cidCod;
    }

    public void setCidCod(Integer cidCod) {
        this.cidCod = cidCod;
    }

    public List<Regiao> getRegiaoList() {
        return regiaoList;
    }

    public void setRegiaoList(List<Regiao> regiaoList) {
        this.regiaoList = regiaoList;
    }

    public List<Bairro> getBairrosList() {
        return bairrosList;
    }

    public void setBairrosList(List<Bairro> bairrosList) {
        this.bairrosList = bairrosList;
    }

    public List<Superficie> getSuperficieList() {
        return superficieList;
    }

    public void setSuperficieList(List<Superficie> superficieList) {
        this.superficieList = superficieList;
    }

    public List<Trilha> getTrilhaList() {
        return trilhaList;
    }

    public void setTrilhaList(List<Trilha> trilhaList) {
        this.trilhaList = trilhaList;
    }

    public List<Waypoint> getWaypointList() {
        return waypointList;
    }

    public void setWaypointList(List<Waypoint> waypointList) {
        this.waypointList = waypointList;
    }

    public Tipo getTipCod() {
        return tipCod;
    }

    public void setTipCod(Tipo tipCod) {
        this.tipCod = tipCod;
    }

    public Dificuldade getDifCod() {
        return difCod;
    }

    public void setDifCod(Dificuldade difCod) {
        this.difCod = difCod;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codt != null ? codt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trilhadados)) {
            return false;
        }
        Trilhadados other = (Trilhadados) object;
        if ((this.codt == null && other.codt != null) || (this.codt != null && !this.codt.equals(other.codt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.udesc.joinville.dcc.bdes.ciclogeoinfo.persistencia.Trilhadados[ codt=" + codt + " ]";
    }
    
}
