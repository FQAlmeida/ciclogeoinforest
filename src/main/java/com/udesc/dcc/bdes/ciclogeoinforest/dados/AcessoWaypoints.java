package com.udesc.dcc.bdes.ciclogeoinforest.dados;

import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.NaoGeografico;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.FeatureInfo;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.DadosLayer;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Trilhadados;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Usuario;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Waypoint;
import com.udesc.dcc.bdes.ciclogeoinforest.persistencia.TrilhaJpaController;
import com.udesc.dcc.bdes.ciclogeoinforest.persistencia.TrilhadadosJpaController;
import com.udesc.dcc.bdes.ciclogeoinforest.persistencia.WaypointJpaController;
import java.awt.Image;
import java.io.*;
import java.lang.String;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Leandro
 */
public class AcessoWaypoints {

    private TrilhaJpaController tjc = null;
    private WaypointJpaController wjc = null;
    private EntityManagerFactory emf = null;
    private TrilhadadosJpaController tdjc = null;

    public AcessoWaypoints(EntityManagerFactory emf){
        this.emf = emf;
    }
    
    /*
     * getTJC: Cria e/ou retorna um controlador de entidade mapeada no banco.
     */
    private TrilhaJpaController getTJC() {

        if (tjc == null) {
            if (emf == null) {
                try {
                    emf = Persistence.createEntityManagerFactory("PU");
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
            tjc = new TrilhaJpaController(emf);
            emf.close();
        }
        return tjc;
    }

    /*
     * getTDJC: Cria e/ou retorna um controlador de entidade mapeada no banco.
     */
    private TrilhadadosJpaController getTDJC() {

        if (tdjc == null) {
            if (emf == null) {
                try {
                    emf = Persistence.createEntityManagerFactory("PU");
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
            tdjc = new TrilhadadosJpaController(emf);
            emf.close();
        }
        return tdjc;
    }

    /*
     * getWJC: Cria e/ou retorna um controlador de entidade mapeada no banco.
     */
    private WaypointJpaController getWJC() {
        if (wjc == null) {
            if (emf == null) {
                try {
                    emf = Persistence.createEntityManagerFactory("PU");
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
            wjc = new WaypointJpaController(emf);
            emf.close();
        }
        return wjc;
    }

    /*
     * inserirWaypoint: Insere os dados dos waypoints no banco.
     */
    public boolean newWaypoint(int codt, String descricao, String nome, String geometria, int usu_cod,
            ArrayList<byte[]> imagens, ArrayList<Integer> categorias) {

        ArrayList<String[]> imgs = new ArrayList();

        /* 
         * o sistema deve armazenar apenas uma imagem por waypoint.
         * Para armazenar mais imagens por waypoint deve-se passar todo o arraylist
         * e fazer as devidas alterações na classe do controlador da entidade.
         */
        byte img[] = null;
        if(imagens.size()>0){
            img = imagens.get(0);
        }//else vai ser null
        return getWJC().create(codt, descricao, nome, geometria, usu_cod, img, categorias);

    }
    
    /*
     * deleteWaypoint: Remove um waypoint do banco.
     */
    public boolean deleteWaypoint(int codwp){
        return getWJC().delete(codwp);
    }
    
    /*
     * findWaypoint: Encontra um waypoint do banco pelo seu id.
     */
    public Waypoint findWaypoint(int codwp){
        return getWJC().findWaypoint(codwp);
    }

    /*
     * Função para download de uma layer do banco
     */
    public List<DadosLayer> downloadLayer(List<Integer> codt) {

        List<Object[]> listaResultado = null;
        Object[] resultado = null;
        List<DadosLayer> dados = new ArrayList<DadosLayer>();
        DadosLayer dl = new DadosLayer();
        List<Double> latitudeWaypoint = new ArrayList<Double>();
        List<Double> longitudeWaypoint = new ArrayList<Double>();
        List<Integer> codwp = new ArrayList<Integer>();
        List<Double> latitudeTrilha = new ArrayList<Double>();
        List<Double> longitudeTrilha = new ArrayList<Double>();
        
        for (int w = 0; w < codt.size(); w++) { 
            String SQL = "SELECT AsText(geometria) AS pontos FROM trilha WHERE codt = " + codt.get(w);
            String SQL2 = "SELECT codwp, AsText(geometria) AS pontos FROM waypoint WHERE codt = " + codt.get(w);
            String temp, aux, separada[];
            double aDouble;
            
            try {

                listaResultado = getWJC().getEntityManager().createNativeQuery(SQL).getResultList();
                getWJC().getEntityManager().close();
                
                for (int cont = 0; cont < listaResultado.size(); cont++) {
                    temp = String.valueOf(listaResultado.get(cont));
                    temp = temp.replace(")", "");
                    temp = temp.replace("LINESTRING(", "");
                    String virg[] = temp.split(",");
                    for (int i = 0; i < virg.length; i++) {
                        String espaco[] = virg[i].split(" ");
                        aDouble = Double.parseDouble(espaco[0]);
                        longitudeTrilha.add(aDouble);
                        aDouble = Double.parseDouble(espaco[1]);
                        latitudeTrilha.add(aDouble);
                    }
                }

                listaResultado = getTJC().getEntityManager().createNativeQuery(SQL2).getResultList();
                getTJC().getEntityManager().close();
                
                for (int cont = 0; cont < listaResultado.size(); cont++) {
                    resultado = listaResultado.get(cont);
                    temp = resultado[1].toString();
                    temp = temp.replace(")", "");
                    temp = temp.replace("POINT(", "");
                    String virg[] = temp.split(",");
                    
                    for (int i = 0; i < virg.length; i++) {
                        String espaco[] = virg[i].split(" ");
                        aDouble = Double.parseDouble(espaco[0]);
                        longitudeWaypoint.add(aDouble);
                        aDouble = Double.parseDouble(espaco[1]);
                        latitudeWaypoint.add(aDouble);
                    }
                    
                    codwp.add(Integer.parseInt(resultado[0].toString()));
                }
                
                dl.setCodwp(codwp);
                dl.setLatitudeTrilha(latitudeTrilha);
                dl.setLongitudeTrilha(longitudeTrilha);
                dl.setLatitudeWaypoint(latitudeWaypoint);
                dl.setLongitudeWaypoint(longitudeWaypoint);
                dados.add(dl);

            } catch (Exception ex) {
                System.out.println(ex.toString());
                getWJC().getEntityManager().close();
                getTJC().getEntityManager().close();
                return null;
            }
        }
        
        return dados;
        
    }

    /*
     * Função para retorno de informações não geograficas
     */
    public java.util.List<NaoGeografico> getNaoGeografico(String tipo, int cod) {

        if (tipo.equals("waypoint"))
            return getNaoGeograficoWayPoint(cod);
        else {
            if (tipo.equals("trilha")) {
                return getNaoGeograficoTrilha(cod);
            } else {
                return null;
            }
        }
    }

    /*
     * Função para retorno de informações não geograficas de Waypoints
     */
    public List<NaoGeografico> getNaoGeograficoWayPoint(int codigo) {

        List<Object[]> listaResultado = null;
        Object[] resultado = null;
        List<Object[]> listaResultado2 = null;
        List<Object[]> listaResultado3 = null;
        List<NaoGeografico> listaNG = new ArrayList<NaoGeografico>();
        
        try {
            
            String SQL = "SELECT descricao, nome, codt FROM waypoint WHERE codwp = " + codigo + " ;";
            System.out.println("getNaoGeograficoWaypoint: sql== " + SQL);
            listaResultado = getWJC().getEntityManager().createNativeQuery(SQL).getResultList();
            
            for (int cont = 0; cont < listaResultado.size(); cont++) {
                resultado = listaResultado.get(cont);
                NaoGeografico ng = new NaoGeografico();
                ng.setDescricao(resultado[0].toString());
                ng.setNome(resultado[1].toString());
                ng.setCod(Integer.parseInt(resultado[2].toString()));
                String SQL2 = "SELECT cat_cod FROM categoriawaypoint WHERE codwp = " + codigo + ";";
                listaResultado2 = getWJC().getEntityManager().createNativeQuery(SQL2).getResultList();
                List<Integer> aux = new ArrayList<Integer>();
                
                for (int cont2 = 0; cont2 < listaResultado2.size(); cont2++)
                    aux.add(Integer.parseInt(String.valueOf(listaResultado2.get(cont2))));
                
                ng.setCategoriaWaypoint(aux);
                String SQL3 = "SELECT COUNT(codimg) as numero FROM imagem WHERE codwp = " + codigo + ";";
                listaResultado3 = getWJC().getEntityManager().createNativeQuery(SQL3).getResultList();
                
                for (int cont2 = 0; cont2 < listaResultado3.size(); cont2++) 
                    ng.setNumeroDeImagens(Integer.parseInt(String.valueOf(listaResultado3.get(cont2))));
                
                listaNG.add(ng);
            }

            getWJC().getEntityManager().close();
            System.out.println("Retornou do banco size eh: " + listaNG.size());
            return listaNG;
            
        } catch (Exception ex) {
            getWJC().getEntityManager().close();
            System.err.println("DEU ERRO: " + ex.getMessage());
        }
        
        System.out.println("Retornou null");
        return null;
        
    }

    public List<NaoGeografico> getNaoGeograficoTrilha(int codigo) {

        List<Object[]> listaResultado = null;
        Object[] resultado = null;
        List<NaoGeografico> listaNG = new ArrayList<NaoGeografico>();
        List<Integer> bairros = new ArrayList<Integer>();
        List<Integer> regioes = new ArrayList<Integer>();
        List<Integer> seperficies = new ArrayList<Integer>();
        
        try {
            
            NaoGeografico ng = new NaoGeografico();
            String SQL = "SELECT comprimento_km, desnivel, nome, descricao, tip_cod, dif_cod, cid_cod FROM trilhadados WHERE codt = " + codigo + ";";
            listaResultado = getTDJC().getEntityManager().createNativeQuery(SQL).getResultList();
            
            for (int cont = 0; cont < listaResultado.size(); cont++) {
                resultado = listaResultado.get(cont);
                ng.setCod(codigo);
                ng.setComprimento(Double.parseDouble(resultado[0].toString()));
                ng.setDesnivel(Double.parseDouble(resultado[1].toString()));
                ng.setNome(resultado[2].toString());
                ng.setDescricao(resultado[3].toString());
                ng.setTip_cod(Integer.parseInt(resultado[4].toString()));
                ng.setDif_cod(Integer.parseInt(resultado[5].toString()));
                ng.setCid_cod(Integer.parseInt(resultado[6].toString()));
                listaNG.add(ng);
            }
            
            String SQL2 = "SELECT bai_cod FROM bairrotrilha WHERE codt = " + codigo + ";";
            listaResultado = getTDJC().getEntityManager().createNativeQuery(SQL2).getResultList();
            
            for (int cont = 0; cont < listaResultado.size(); cont++)
                bairros.add(Integer.parseInt(String.valueOf(listaResultado.get(cont))));
            
            ng.setBairros(bairros);
            String SQL3 = "SELECT reg_cod FROM regiaotrilha WHERE codt = " + codigo + ";";
            listaResultado = getTDJC().getEntityManager().createNativeQuery(SQL3).getResultList();
            
            for (int cont = 0; cont < listaResultado.size(); cont++)
                regioes.add(Integer.parseInt(String.valueOf(listaResultado.get(cont))));
            
            ng.setRegioes(regioes);
            String SQL4 = "SELECT sup_cod FROM superficietrilha WHERE codt = " + codigo + ";";
            listaResultado = getTDJC().getEntityManager().createNativeQuery(SQL4).getResultList();
            
            for (int cont = 0; cont < listaResultado.size(); cont++)
                seperficies.add(Integer.parseInt(String.valueOf(listaResultado.get(cont))));
            
            ng.setSuperficies(seperficies);
            getTDJC().getEntityManager().close();
            return listaNG;
            
        } catch (Exception ex) {
            System.err.println("ERRO: " + ex.getMessage());
            getTDJC().getEntityManager().close();
        }
        
        return null;
        
    }

    /*
     * Função para buscar as trilhas de um definidas pelos filtros
     */
    public List<FeatureInfo> buscaTrilhas(List<Integer> tipo, List<Integer> dificuldade,
            List<Integer> bairro, List<Integer> regiao, List<Integer> superficie, List<Integer> categoria) {
        List<Object[]> listaResultado = null;
        Object[] resultado = null;
        String query = "SELECT DISTINCT t.codt,t.nome FROM trilhadados t";
        String tempQuery;
        ArrayList<String> WHEREQuery = new ArrayList<String>();
        
        if (!tipo.isEmpty()&& tipo.get(0) != null) {
         //   tempQuery = "t.codt IN (SELECT a0.codt FROM ";
           // tempQuery = tempQuery + "(SELECT DISTINCT tipo.codt FROM tipo WHERE tipo.tip_cod=" + tipo.get(0) + ")a0";
            String tipos="(";
            for (int i=0;i<tipo.size();i++){
                if(i==0) tipos+=tipo.get(i);
                else{
                    tipos+=","+tipo.get(i);
                }
            }
            tipos+=")";
            tempQuery = "t.tip_cod IN "+tipos;
          //  tempQuery = tempQuery + " NATURAL INNER JOIN (SELECT DISTINCT tipo.codt FROM tipo WHERE tipo.tip_cod IN "+ tipos + ")a1";
            WHEREQuery.add(tempQuery);
        }        
        if (!dificuldade.isEmpty()&& dificuldade.get(0) != null) {
            //tempQuery = "t.codt IN (SELECT a0.codt FROM ";
            //tempQuery = tempQuery + "(SELECT DISTINCT d.codt FROM dificuldade d WHERE d.dif_cod=" + dificuldade.get(0) + ")a0";
            String dificuldades="(";
            for (int i =0;i<dificuldade.size();i++){
                if(i==0) dificuldades+=dificuldade.get(i);
                else{
                    dificuldades+=","+dificuldade.get(i);
                }
            }
            dificuldades+=")";
            tempQuery = "t.dif_cod IN "+dificuldades;
            //tempQuery = tempQuery + " NATURAL INNER JOIN (SELECT DISTINCT d.codt FROM dificuldade d WHERE d.dif_cod IN "+ dificuldades + ")a1";
            WHEREQuery.add(tempQuery);
            System.out.println(tempQuery);
        }
        
        if (!superficie.isEmpty()&& superficie.get(0) != null) {
            tempQuery = "t.codt IN (SELECT a0.codt FROM ";
            tempQuery = tempQuery + "(SELECT DISTINCT s.codt FROM superficietrilha s WHERE s.sup_cod=" + superficie.get(0) + ")a0";
            String superficies="(";
            for (int i =1;i<superficie.size();i++){
                if(i==1) superficies+=superficie.get(i);
                else{
                    superficies+=","+superficie.get(i);
                }
            }
            superficies+=")";
            tempQuery = tempQuery + " NATURAL INNER JOIN (SELECT DISTINCT s.codt FROM superficietrilha s WHERE s.sup_cod IN "+ superficies + ")a1";
            WHEREQuery.add(tempQuery + ")");
        }
        
        if (!regiao.isEmpty() && regiao.get(0) != null ) {
            tempQuery = "t.codt IN (SELECT a0.codt FROM ";
            tempQuery = tempQuery + "(SELECT DISTINCT r.codt FROM regiaotrilha r WHERE r.reg_cod=" + regiao.get(0) + ")a0";
            String regioes="(";
            for (int i =1;i<regiao.size();i++){
                if(i==1) regioes+=regiao.get(i);
                else{
                    regioes+=","+regiao.get(i);
                }
            }
            regioes+=")";
            tempQuery = tempQuery + " NATURAL INNER JOIN (SELECT DISTINCT r.codt FROM regiaotrilha r WHERE r.reg_cod IN " + regioes + ")a1";
            WHEREQuery.add(tempQuery + ")");
        }
        
        if (!bairro.isEmpty() && bairro.get(0) != null) {
            tempQuery = "t.codt IN (SELECT a0.codt FROM ";
            tempQuery = tempQuery + "(SELECT DISTINCT b.codt FROM bairrotrilha b WHERE b.bai_cod=" + bairro.get(0) + ")a0";
            String bairros="(";
            for (int i =1;i<bairro.size();i++){
                if(i==1) bairros+=bairro.get(i);
                else{
                    bairros+=","+bairro.get(i);
                }
            }
            bairros+=")";
            tempQuery = tempQuery + " NATURAL INNER JOIN (SELECT DISTINCT b.codt FROM bairrotrilha b WHERE b.bai_cod IN " + bairros + ")a1";
            WHEREQuery.add(tempQuery + ")");
        }
        
        if (!categoria.isEmpty() && categoria.get(0) != null) {
            tempQuery = "t.codt IN (SELECT a0.codt FROM ";
            tempQuery = tempQuery + "(SELECT DISTINCT w.codt FROM waypoint w WHERE w.codwp IN (SELECT c.codwp FROM categoriawaypoint c WHERE c.cat_cod=" + categoria.get(0) + "))a0";
            String categorias="(";
            for (int i =1;i<categoria.size();i++){
                if(i==1) categorias+=categoria.get(i);
                else{
                    categorias+=","+categoria.get(i);
                }
            }
            categorias+=")";
            tempQuery = tempQuery + " NATURAL INNER JOIN (SELECT DISTINCT w.codt FROM waypoint w WHERE w.codwp IN (SELECT c.codwp FROM categoriawaypoint c WHERE c.cat_cod IN " + categorias + "))a1";
            WHEREQuery.add(tempQuery + ")");
        }
        
        query = query + " WHERE t.codt IS NOT NULL";
        
        for (String s : WHEREQuery)
            query = query + " AND " + s;

        List<FeatureInfo> listaLT = new ArrayList<FeatureInfo>();
        
        try {
            
            listaResultado = getTDJC().getEntityManager().createNativeQuery(query).getResultList();
            getTDJC().getEntityManager().close();
            
            for (int cont = 0; cont < listaResultado.size(); cont++) {
                FeatureInfo fi = new FeatureInfo();
                resultado = listaResultado.get(cont);
                fi.setCod(Integer.parseInt(resultado[0].toString()));
                fi.setNome(resultado[1].toString());
                fi.setTipo("trilha");
                listaLT.add(fi);
            }
            System.out.println("chegou aki");
            return listaLT;
            
        } catch (Exception ex) {
            System.out.println("ERRO: " + ex.getMessage());
            getTDJC().getEntityManager().close();
        }
        System.out.println("ou aki");
        return null;
        
    }
     /*
     * Função para buscar as trilhas de um user
     */
    public List<FeatureInfo> getTrilhasUser(String email) {

        List<Object[]> listaResultado = null;
        Object[] resultado = null;        
        Usuario usu;
            usu = (Usuario) getTDJC().getEntityManager().createNamedQuery("Usuario.findByUsuLogin").setParameter("usuEmail", email).getSingleResult();
            
            String query = "SELECT trilhadados.codt,trilhadados.nome "
                + " FROM trilhadados "
                + " INNER JOIN usuariotrilha ON trilhadados.codt=usuariotrilha.codt "
                + " AND usuariotrilha.usu_cod="+usu.getUsuCod()+";";
     List<FeatureInfo> listaLT = new ArrayList<FeatureInfo>();
        
        try {
            
            listaResultado = getTDJC().getEntityManager().createNativeQuery(query).getResultList();
            getTDJC().getEntityManager().close();
            
            for (int cont = 0; cont < listaResultado.size(); cont++) {
                FeatureInfo fi = new FeatureInfo();
                resultado = listaResultado.get(cont);
                fi.setCod(Integer.parseInt(resultado[0].toString()));
                fi.setNome(resultado[1].toString());
                fi.setTipo("trilha");
                listaLT.add(fi);
            }
            
            return listaLT;
            
        } catch (Exception ex) {
            System.out.println("ERRO: " + ex.getMessage());
            getTDJC().getEntityManager().close();
        }
        
        return null;
        
    }
    
      /*
     * Função para buscar os waypoints de um user
     */
    public List<FeatureInfo> getWaypointsUser(String email) {

        System.out.println("Retornando waypointsUser");
        List<Object[]> listaResultado = null;
        Object[] resultado = null;        
        Usuario usu;
            usu = (Usuario) getTDJC().getEntityManager().createNamedQuery("Usuario.findByUsuLogin").setParameter("usuEmail", email).getSingleResult();
            
            String query = "SELECT waypoint.codwp,waypoint.nome " +
                           "FROM waypoint " +
                           "WHERE waypoint.usu_cod="+usu.getUsuCod()+" ORDER BY codwp DESC;";
            
     List<FeatureInfo> listaLT = new ArrayList<FeatureInfo>();
        
        try {
            
            listaResultado = getTDJC().getEntityManager().createNativeQuery(query).getResultList();
            getTDJC().getEntityManager().close();
            
            for (int cont = 0; cont < listaResultado.size(); cont++) {
                FeatureInfo fi = new FeatureInfo();
                resultado = listaResultado.get(cont);
                fi.setCod(Integer.parseInt(resultado[0].toString()));
                fi.setNome(resultado[1].toString());
                fi.setTipo("waypoint");
                listaLT.add(fi);
            }
            
            return listaLT;
            
        } catch (Exception ex) {
            System.out.println("ERRO: " + ex.getMessage());
            getTDJC().getEntityManager().close();
        }
        
        return null;
        
    }
    

  /*
     * Função para getFeatureInfo
     */
    public java.util.List<FeatureInfo> getFeatureInfo(double bbox1, double bbox2, double bbox3, double bbox4, int x, int y, int srs, int widthh, int heightt) {

        List<Object[]> listaResultado = null;
        String url_imagem;
        url_imagem = "http://bdes.dcc.joinville.udesc.br:8443/geoserver/wms?"
        //url_imagem = "http://localhost:8084/geoserver/wms?"
                + "REQUEST=GetFeatureInfo&"
                + "STYLES=&"
                + "LAYERS=bicicletav4&"
                + "QUERY_LAYERS=bicicletav4&"
                + "SRS=EPSG%3A" + srs + "&"
                + "FORMAT=image%2Fpng"
                + "EXCEPTIONS=application%2Fvnd.ogc.se_xml&"
                + "BBOX=" + bbox1 + "%2C" + bbox2 + "%2C" + bbox3 + "%2C" + bbox4 + "&"
                + "X=" + x + "&"
                + "Y=" + y + "&"
                + "INFO_FORMAT=text%2Fhtml&"
                + "FEATURE_COUNT=50&"
                + "WIDTH=" + widthh + "&"
                + "HEIGHT=" + heightt + "&"
                ;
System.out.println("url_acessoWaypoints: "+ url_imagem);

        List<FeatureInfo> lista = new ArrayList<FeatureInfo>();
        
        try {

            URL url = new URL(url_imagem);
            URLConnection conn = url.openConnection();
            InputStream is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String linha = br.readLine();
            
//            File f = new File("C:/Users/Leandro/Desktop/file.txt");
//            FileOutputStream fos = new FileOutputStream(f);
//            fos.write((linha).getBytes());
//            while(linha != null) {
//                linha = br.readLine();
//                fos.write((linha).getBytes());
//            }
//            fos.close();
            
        System.out.println(linha);
            
            while (linha != null) {
//if (linha.equals("  <caption class=\"featureInfo\">trilhav4</caption>")) {
                if (linha.equals("  <caption class=\"featureInfo\">trilhav4</caption>")) {
                    while (!linha.equals("  <caption class=\"featureInfo\">waypointv4</caption>")) {
                        FeatureInfo featureInfoN = new FeatureInfo();
                        if (linha.endsWith("</td>")) {
                            linha.replace("      <td>", " ");
                            linha.replace("</td>", " ");
                            int t = Integer.parseInt(linha.substring(linha.indexOf("<td>") + 4, linha.indexOf("</td>")));
                            featureInfoN.setTipo("trilha");
                            featureInfoN.setCod(t);
                            String SQL = "SELECT nome FROM trilhadados WHERE codt = " + t + ";";
                            
                            try {
                                listaResultado = getTDJC().getEntityManager().createNativeQuery(SQL).getResultList();
                                getTDJC().getEntityManager().close();
                                featureInfoN.setNome(String.valueOf(listaResultado.get(0)));
                            } catch (Exception ex) {
                                System.out.println("ERRO1: " + ex.getMessage());
                                getTDJC().getEntityManager().close();
                            }
                            
                            lista.add(featureInfoN);
                            linha = br.readLine();
                        }
                        
                        linha = br.readLine();
                        
                    }
                }
//if (linha.equals("  <caption class=\"featureInfo\">waypointv4</caption>")) {
                if (linha.equals("  <caption class=\"featureInfo\">waypointv4</caption>")) {
                    
                    while (linha != null) {
                        
                        FeatureInfo featureInfoN = new FeatureInfo();

                        if (linha.startsWith("  <td>waypoint")) {
                            int t = Integer.parseInt(linha.substring(linha.indexOf("<td>") + 13, linha.indexOf("</td>")));
                            linha = br.readLine();
                            int codtWaypoint = Integer.parseInt(linha.substring(linha.indexOf("<td>") + 4, linha.indexOf("</td>")));
                            linha = br.readLine();
                            linha = br.readLine();
                            featureInfoN.setTipo("waypoint");
                            featureInfoN.setCod(t);
                            featureInfoN.setCodtFromWaypoint(codtWaypoint);
                            featureInfoN.setNome(linha.substring(linha.indexOf("<td>") + 4, linha.indexOf("</td>")));
                            lista.add(featureInfoN);
                            linha = br.readLine();
                        }
                        
                        linha = br.readLine();
                        
                    }
                }

                linha = br.readLine();  

            }
        } catch (Exception ex) {
            System.err.println("ERRO2: " + ex.getMessage());
        }
        
        return lista;
        
    }
    
    
     /*
     * Função para getFeatureInfoProximo
     */
    public java.util.List<FeatureInfo> getFeatureInfoProximo(double latitude, double longitude) {
        List<FeatureInfo> lista = new ArrayList<FeatureInfo>();        
        List<Integer> codts;
        String query_distancia =   "SELECT DISTINCT codt FROM trilha "
                                    +" WHERE ST_distance_sphere(geometria, ST_GeomFromText("
                                    + "'POINT("+ latitude+" "+longitude+")',4326)) < 30 ORDER BY codt DESC;";
        System.out.println(query_distancia);
        try {
            codts = getTDJC().getEntityManager().createNativeQuery(query_distancia).getResultList();
            getTDJC().getEntityManager().close();
            System.out.println("size do distancia == " + codts.size());

            for (int cont = 0; cont < codts.size(); cont++) {
               
                Trilhadados trilha = (Trilhadados) getTDJC().getEntityManager().createNamedQuery("Trilhadados.findByCodt").setParameter("codt", codts.get(cont)).getSingleResult();
                
                FeatureInfo fi  = new FeatureInfo();
                fi.setCod(trilha.getCodt());
                fi.setNome(trilha.getNome());                
                if(fi!=null){
                    lista.add(fi);
                }
            }
            
            return lista;
            
        } catch (Exception ex) {
            System.out.println("ERRO: " + ex.getMessage());
            getTDJC().getEntityManager().close();
        }
        
        return null;
        
    }
    
    
    /*
     * analisarArquivoWP: analisa um arquivo e encontra strings geográficas sobre o Waypoint
     * POSSIVEL ALTERAÇÃO FUTURA NECESSÁRIA: enviar um arquivo com vários waypoints.
     */
    public static ArrayList<String> analisarArquivoWP(File arq) {
        String linha, retorno = "", temp1, temp2;
        BufferedReader br;
        ArrayList<String> infoWaypoint = new ArrayList<String>();

        ArrayList<String> geometria = new ArrayList<String>();
        
        try {
            br = new BufferedReader(new FileReader(arq));
            int totwp = 0;
            //Contar qtos wp tem no arquivo
            while (!(linha = br.readLine()).startsWith("Track")) {
                if (linha.startsWith("Waypoint")) {
                    infoWaypoint.add(linha.substring(9));
                    totwp++;
                }
            }
            br.close();


            for (int i = 0; i < totwp; i++) {

                linha = infoWaypoint.get(i);

                linha = linha.substring(linha.indexOf('\t'), linha.length());
                while (linha.startsWith("\t")) {
                    linha = linha.substring(1);
                }

                linha = linha.substring(linha.indexOf('\t'), linha.length());
                while (linha.startsWith("\t")) {
                    linha = linha.substring(1);
                }
                while (linha.startsWith("User Waypoint")) {
                    linha = linha.substring(13);
                }
                while (linha.startsWith("\t")) {
                    linha = linha.substring(1);
                }
                temp1 = linha.substring(0, 10).replace('S', '-');
                temp1 = temp1.substring(0, 3) + "." + (("" + ((Float.parseFloat(temp1.substring(4))) / 60)).substring(2));
                temp2 = linha.substring(11, linha.indexOf('\t')).replace('W', '-');
                temp2 = temp2.substring(0, 3) + "." + (("" + ((Float.parseFloat(temp2.substring(4))) / 60)).substring(2));
                
                geometria.add(temp2 + " " + temp1);
            }


        } catch (Exception ex) {
            retorno = ex.toString();
        }
        return geometria;
    }

    /*
     * getImage: Faz acesso às imagens para a busca de uma imagem dos waypoints.
     */
    public byte[] getByteImage(int codimg, int codwp) {
        AcessoImagens imagem = new AcessoImagens(emf);
        byte[] img = imagem.getByteImage(codimg, codwp);
        return img;
    }
    
    public Image getImage(int codimg, int codwp) {
        AcessoImagens imagem = new AcessoImagens(emf);
        Image img = imagem.getImage(codimg, codwp);
        return img;
    }

}
