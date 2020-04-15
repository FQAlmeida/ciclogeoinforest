/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects;

import com.udesc.dcc.bdes.ciclogeoinforest.persistencia.ImagemPK;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "imagem")
@NamedQueries({
    @NamedQuery(name = "Imagem.findAll", query = "SELECT i FROM Imagem i"),
    @NamedQuery(name = "Imagem.findByCodwp", query = "SELECT i FROM Imagem i WHERE i.imagemPK.codwp = :codwp"),
    @NamedQuery(name = "Imagem.findByCodimg", query = "SELECT i FROM Imagem i WHERE i.imagemPK.codimg = :codimg"),
    @NamedQuery(name = "Imagem.findByImg", query = "SELECT i FROM Imagem i WHERE i.img = :img")})
public class Imagem implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ImagemPK imagemPK;
    @Basic(optional = false)
    @Column(name = "img")
    private long img;
    @Column(name = "usu_cod")
    private Integer usuCod;
    @JoinColumn(name = "codwp", referencedColumnName = "codwp", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Waypoint waypoint;
    @Column(name = "bytes")
    private byte[] bytes;

    public Imagem() {
    }

    public Imagem(ImagemPK imagemPK) {
        this.imagemPK = imagemPK;
    }

    public Imagem(ImagemPK imagemPK, long img) {
        this.imagemPK = imagemPK;
        this.img = img;
    }

    public Imagem(int codwp, int codimg) {
        this.imagemPK = new ImagemPK(codwp, codimg);
    }

    public ImagemPK getImagemPK() {
        return imagemPK;
    }

    public void setImagemPK(ImagemPK imagemPK) {
        this.imagemPK = imagemPK;
    }

    public long getImg() {
        return img;
    }

    public void setImg(long img) {
        this.img = img;
    }

    public Waypoint getWaypoint() {
        return waypoint;
    }

    public void setWaypoint(Waypoint waypoint) {
        this.waypoint = waypoint;
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
        hash += (imagemPK != null ? imagemPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Imagem)) {
            return false;
        }
        Imagem other = (Imagem) object;
        if ((this.imagemPK == null && other.imagemPK != null) || (this.imagemPK != null && !this.imagemPK.equals(other.imagemPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.udesc.joinville.dcc.bdes.ciclogeoinfo.persistencia.Imagem[ imagemPK=" + imagemPK + " ]";
    }
    
}
