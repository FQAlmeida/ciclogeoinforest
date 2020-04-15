package com.udesc.dcc.bdes.ciclogeoinforest.persistencia;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Leandro
 */
@Embeddable
public class ImagemPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "codwp")
    private int codwp;
    @Basic(optional = false)
    @Column(name = "codimg")
    private int codimg;

    public ImagemPK() {
    }

    public ImagemPK(int codwp, int codimg) {
        this.codwp = codwp;
        this.codimg = codimg;
    }

    public int getCodwp() {
        return codwp;
    }

    public void setCodwp(int codwp) {
        this.codwp = codwp;
    }

    public int getCodimg() {
        return codimg;
    }

    public void setCodimg(int codimg) {
        this.codimg = codimg;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codwp;
        hash += (int) codimg;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ImagemPK)) {
            return false;
        }
        ImagemPK other = (ImagemPK) object;
        if (this.codwp != other.codwp) {
            return false;
        }
        if (this.codimg != other.codimg) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.udesc.joinville.dcc.bdes.ciclogeoinfo.persistencia.ImagemPK[ codwp=" + codwp + ", codimg=" + codimg + " ]";
    }
    
}
