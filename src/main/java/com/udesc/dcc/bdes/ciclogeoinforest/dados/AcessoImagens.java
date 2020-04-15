package com.udesc.dcc.bdes.ciclogeoinforest.dados;

import com.udesc.dcc.bdes.ciclogeoinforest.persistencia.ImagemJpaController;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.ImageIcon;

/**
 *
 * @author Leandro
 */
public class AcessoImagens {

    private ImagemJpaController ijc = null;
    private EntityManagerFactory emf = null;

    public AcessoImagens(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    /*
     * getIJC: Cria e/ou retorna um controlador de entidade mapeada no banco.
     */
    private ImagemJpaController getIJC() {
        if (ijc == null) {
            if (emf == null) {
                try {
                    emf = Persistence.createEntityManagerFactory("PU");
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            ijc = new ImagemJpaController(emf);
            emf.close();
        }
        return ijc;
    }

    /*
     * getImage: método pegar imagem do banco
     */
    public byte[] getByteImage(int codimg, int codwp) {

        Image img = null;
        byte[] listaB = null;
        
        if (codwp == -1) {
            
            String SQL = "SELECT imgba FROM trilhadados WHERE codt = " + codimg;
            try {
                listaB = (byte[])getIJC().getEntityManager().createNativeQuery(SQL).getSingleResult();
                getIJC().getEntityManager().close();
                return listaB;
            } catch (Exception ex) {
                getIJC().getEntityManager().close();
                System.out.println("ERROR: " + ex.getMessage());
            }
        } else {
            
            String SQL = "SELECT bytes FROM imagem WHERE codwp = " + codwp + " and codimg = " + codimg;
            
            try {
                listaB = (byte[])getIJC().getEntityManager().createNativeQuery(SQL).getSingleResult();
                getIJC().getEntityManager().close();
                return listaB;
            } catch (Exception ex) {
                getIJC().getEntityManager().close();
                System.out.println("ERROR: " + ex.getMessage());
            }
        }
        
        getIJC().getEntityManager().close();
        return null;

    }
    
    public Image getImage(int codimg, int codwp) {

        byte[] listaB = null;
        
        if (codwp == -1) {
            
            String SQL = "SELECT imgba FROM trilhadados WHERE codt = " + codimg;
            try {
                listaB = (byte[])getIJC().getEntityManager().createNativeQuery(SQL).getSingleResult();
                getIJC().getEntityManager().close();
                return new ImageIcon(listaB).getImage();
            } catch (Exception ex) {
                getIJC().getEntityManager().close();
                System.out.println("ERROR: " + ex.getMessage());
            }
        } else {
            
            String SQL = "SELECT bytes FROM imagem WHERE codwp = " + codwp + " and codimg = " + codimg;
            
            try {
                listaB = (byte[])getIJC().getEntityManager().createNativeQuery(SQL).getSingleResult();
                getIJC().getEntityManager().close();
                return new ImageIcon(listaB).getImage();
            } catch (Exception ex) {
                getIJC().getEntityManager().close();
                System.out.println("ERROR: " + ex.getMessage());
            }
        }
        
        getIJC().getEntityManager().close();
        return null;

    }

    /*
     * showImage: recebe uma imagem bufferizada e a retorna como um objeto Image
     */
    private Image showImage(BufferedImage image) {

        int width = image.getWidth() / 2;
        int height = image.getHeight() / 2;
        Image img = new ImageIcon(image).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
        return img;

    }
    
}
