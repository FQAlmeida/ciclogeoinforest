package com.udesc.dcc.bdes.ciclogeoinforest.negocio;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

/**
 *
 * @author Leandro G. Destro
 */
public class ManipuladorByteFiles {
    
    /*
     * imageToByte: Recebe uma Image e transforma em array de bytes.
     */
    public static byte[] imageToByte(Image image) {

        BufferedImage bi = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics bg = bi.getGraphics();
        bg.drawImage(image, 0, 0, null);
        bg.dispose();
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        
        try {
            ImageIO.write(bi, "PNG", buff);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return buff.toByteArray();
        
    }
    
    /*
     * byteArrayToFile: Transforma um array de bytes em um arquivo.
     * INUTILIZADO -> NÃO PODEMOS ESCREVER NADA NO DISCO DO SERVIDOR.
     */
//    public static String byteArrayToFile(byte[] array) throws FileNotFoundException, IOException {
//        
//        String URL = "C:/Users/Public/fotosCicloGeoInfo/temp/" + System.currentTimeMillis();
//        FileOutputStream fos = new FileOutputStream(URL);
//        fos.write(array);
//        File f = new File(URL);
//        f.deleteOnExit();
//        return URL;
//        
//    }
    
    /*
     * fileToByteArray: Transforma um arquivo em array de bytes.
     */
    public static byte[] fileToByteArray(File file) throws FileNotFoundException, IOException {

        InputStream is = new FileInputStream(file);
        long length = file.length();

        if (length > Integer.MAX_VALUE) {
            System.out.println("Sorry! Your given file is too large.");
            System.exit(0);
        }

        byte[] bytes = new byte[(int) length];
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead = is.read(bytes,
                offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "
                    + file.getName());
        }
        is.close();
        return bytes;

    }
    
    /*
     * storeJpgImageOnDiskFromByteArray: Recebe um array de bytes e cria um arquivo .jpg a partir dele.
     * INUTILIZADO -> NÃO PODEMOS ESCREVER NADA NO DISCO DO SERVIDOR.
     */
//    public static String[] storeJpgImageOnDiskFromByteArray(byte[] ba) throws IOException {
//        
//        String nomeImg = null;
//        nomeImg = "img" + System.currentTimeMillis() + ".jpg";
//        String caminho = "C:/Users/Public/fotosCicloGeoInfo/temp/";
//        boolean success  = new File(caminho).mkdir();
//        BufferedImage img = null;
//        
//        String[] URL = new String[2];
//        URL[0] = caminho;
//        URL[1] = nomeImg;
//        
//        AcessoImagens i = new AcessoImagens();   
//        img = ImageIO.read(new ByteArrayInputStream(ba));
//        File f = new File(caminho + nomeImg);
//        ImageIO.write(img, "JPG", f);
//        f.deleteOnExit();
//        
//        return URL;
//        return null;
//        
//    }
    
    /*
     * storePngImageOnDiskFromByteArray: Recebe um array de bytes e cria um arquivo .png a partir dele.
     * INUTILIZADO -> NÃO PODEMOS ESCREVER NADA NO DISCO DO SERVIDOR.
     */
//    public static String[] storePngImageOnDiskFromByteArray(byte[] ba) throws IOException {
//        
//        String nomeImg = null;
//        nomeImg = "img" + System.currentTimeMillis() + ".png";
//        String caminho = "C:/Users/Public/fotosCicloGeoInfo/temp/";
//        boolean success  = new File(caminho).mkdir();
//        BufferedImage img = null;
//        
//        String[] URL = new String[2];
//        URL[0] = caminho;
//        URL[1] = nomeImg;
//        
//        AcessoImagens i = new AcessoImagens();      
//        img = ImageIO.read(new ByteArrayInputStream(ba));
//        File f = new File(caminho + nomeImg);
//        ImageIO.write(img, "PNG", f);
//        f.deleteOnExit();
//        
//        return URL;
//        
//        return null;
//        
//    }
    
}
