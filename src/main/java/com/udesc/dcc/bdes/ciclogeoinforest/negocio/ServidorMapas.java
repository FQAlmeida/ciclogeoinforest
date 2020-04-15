package com.udesc.dcc.bdes.ciclogeoinforest.negocio;

import java.awt.Image;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author Leandro Lisura
 */
public class ServidorMapas {
    
    public byte[] getOSMMap (double maxlat, double minlon, double maxlon, double minlat,
            int scale, int zoom){
        
        byte[] bytes = null;
        Image image = null;
        
        String link = "http://www.openstreetmap.org/export/finish?"
                + "maxlat="+maxlat+"&"
                + "minlon="+minlon+"&"
                + "maxlon="+maxlon+"&"
                + "minlat="+minlat+"&"
                + "format=osmarender&"
                + "mapnik_format=jpeg&"
                + "mapnik_scale="+scale+"&"
                + "osmarender_format=png&"
                + "osmarender_zoom="+zoom+"&"
                + "commit=Exportar";
        
        try {    
            URL url = new URL(link);
            image = ImageIO.read(url);
            bytes = ManipuladorByteFiles.imageToByte(image);
            return bytes;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }
    
}