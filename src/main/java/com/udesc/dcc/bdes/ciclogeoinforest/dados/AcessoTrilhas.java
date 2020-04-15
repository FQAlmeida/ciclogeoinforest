package com.udesc.dcc.bdes.ciclogeoinforest.dados;

import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.NaoGeografico;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.TrilhaTransferencia;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Trilhadados;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Usuario;
import com.udesc.dcc.bdes.ciclogeoinforest.negocio.ManipuladorByteFiles;
import com.udesc.dcc.bdes.ciclogeoinforest.persistencia.TrilhaJpaController;
import com.udesc.dcc.bdes.ciclogeoinforest.persistencia.TrilhadadosJpaController;
import java.awt.Image;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Leandro
 */
public class AcessoTrilhas {

    private EntityManagerFactory emf = null;
    private TrilhadadosJpaController tdjc = null;
    private TrilhaJpaController tjc = null;

    public AcessoTrilhas(EntityManagerFactory emf) {
        this.emf = emf;
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

    /* newTrilha:
     * Insere os dados da trilha no banco.
     */
    /*este é o método antigo, onde o arquivo da trilha deve ser interpretado no cliente*/
    public boolean newTrilha(float comprimento, int desnivel, String nome,
            String descricao,byte[] graf_altitude,
            int tip_cod, int dif_cod, int cid_cod, int usu_cod,
            ArrayList<String> geometria, ArrayList<Integer> bairros,
            ArrayList<Integer> regioes, ArrayList<Integer> superficies) {

        return getTDJC().create(String.valueOf(comprimento), desnivel, nome,
                descricao,graf_altitude, tip_cod, dif_cod,
                cid_cod, usu_cod, geometria, bairros, regioes, superficies);

    }
    
    //insere uma trilha recebida pelo android
    public int newTrilha(String comprimento, int desnivel, String nome,
            String descricao, int tip_cod, int dif_cod, int cid_cod, int usu_cod,
            ArrayList<String> geometria, ArrayList<Integer> bairros,
            ArrayList<Integer> regioes, ArrayList<Integer> superficies, String email,
            String timestampString, byte[] graficoAltitude, ArrayList<Double> altitudes) {

        //testar se so passando null resolve
        return getTDJC().create(comprimento, desnivel, nome, descricao,
                graficoAltitude, tip_cod, dif_cod, cid_cod, usu_cod, geometria,
                bairros, regioes, superficies, email, timestampString, altitudes);

    }
    
    public boolean newTrilha(FileReader arquivo_Trilha,byte[] graf_altitude,
            int tip_cod, int dif_cod, int cid_cod, int usu_cod, String descricao,
            ArrayList<Integer> bairros, ArrayList<Integer> regioes, ArrayList<Integer> superficies) {

        try {

            TrilhaTransferencia tt = new TrilhaTransferencia(new BufferedReader(arquivo_Trilha));
            return getTDJC().create(String.valueOf(tt.getComprimento_km()), tt.getDesnivel(), tt.getNome(), descricao,graf_altitude, tip_cod, dif_cod, cid_cod, usu_cod, tt.getGeomPartes(), bairros, regioes, superficies);

        } catch (Exception ex) {
            Logger.getLogger(AcessoTrilhas.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    /*
     * deleteTilha: Remove uma trilha do banco.
     */
    public boolean deleteTrilha(int trilhadadosCodt) {
        return getTDJC().delete(trilhadadosCodt);
    }

    /*
     * findTrilha: Busca uma trilha no banco pelo seu id.
     */
    public Trilhadados findTrilha(int idTrilha) {
        return getTDJC().findTrilhadados(idTrilha);
    }

     /*
     * Retorna uma imagem de determinada região em forma de mapa
     */
    public Image getMap(int srs, boolean transparent, boolean tiled, double bbox1, double bbox2, double bbox3, double bbox4, int widthh, int heightt, List<Integer> cql_filter) {
        String filtros;
        if (cql_filter == null || cql_filter.isEmpty()) {
            filtros = "codt%20%3E%20-1";
        } else {
            filtros = "codt=" + cql_filter.get(0);
            for (int i = 1; i < cql_filter.size(); i++) {
                filtros += "%20OR%20codt%3D" + cql_filter.get(i);
            }
        }
        Image image = null;
        String url_imagem = null;
        url_imagem = "http://bdes.dcc.joinville.udesc.br:8443/geoserver/wms?"
        //url_imagem= "http://localhost:8084/geoserver/wms?"
                + "LAYERS=bicicletav4&"
                + "STYLES=&"
                + "SRS=EPSG%3A" + srs + "&"
                + "FORMAT=image%2Fpng&"
                + "TRANSPARENT=" + transparent + "&"
                + "TILED=" + tiled + "&"
                + "TILESORIGIN=-20037508%2C-20037508&"
                + "SERVICE=WMS&"
                + "VERSION=1.1.1&"
                + "REQUEST=GetMap&"
                + "EXCEPTIONS=application%2Fvnd.ogc.se_inimage&"
                + "CQL_FILTER=" + filtros + "&"
                + "BBOX=" + bbox1 + "," + bbox2 + "," + bbox3 + "," + bbox4 + "&"
                + "WIDTH=" + widthh + "&"
                + "HEIGHT=" + heightt;
System.out.println("GETMAP consult= "+ url_imagem);
        try {
            URL url = new URL(url_imagem);
            image = ImageIO.read(url);
            return image;
        } catch (Exception e) {
            return null;
        }

    }

    public byte[] getByteMap(int srs, boolean transparent, boolean tiled, double bbox1, double bbox2, double bbox3, double bbox4, int widthh, int heightt, List<Integer> cql_filter) {
        String filtros;
        if (cql_filter == null || cql_filter.isEmpty()) {
            filtros = "codt%20%3E%20-1";
        } else {
            filtros = "codt=" + cql_filter.get(0);
            for (int i = 1; i < cql_filter.size(); i++) {
                filtros += "%20OR%20codt%3D" + cql_filter.get(i);
            }
        }
        byte[] bytes = null;
        Image image = null;
        String url_imagem = null;
        url_imagem = "http://bdes.dcc.joinville.udesc.br:8443/geoserver/wms?"
        //url_imagem = "http://localhost:8084/geoserver/wms?"
                + "LAYERS=bicicletav4&"
                + "STYLES=&"
                + "SRS=EPSG%3A" + srs + "&"
                + "FORMAT=image%2Fpng&"
                + "TRANSPARENT=" + transparent + "&"
                + "TILED=" + tiled + "&"
                + "TILESORIGIN=-20037508%2C-20037508&"
                + "SERVICE=WMS&"
                + "VERSION=1.1.1&"
                + "REQUEST=GetMap&"
                + "EXCEPTIONS=application%2Fvnd.ogc.se_inimage&"
                + "CQL_FILTER=" + filtros + "&"
                + "BBOX=" + bbox1 + "," + bbox2 + "," + bbox3 + "," + bbox4 + "&"
                + "WIDTH=" + widthh + "&"
                + "HEIGHT=" + heightt;

        try {
            URL url = new URL(url_imagem);
            image = ImageIO.read(url);
            bytes = ManipuladorByteFiles.imageToByte(image);
            return bytes;
        } catch (Exception e) {
            return null;
        }

    }

    /*
     * Calcula uma rota e a armazena na tabela rout_tmp
     * A rota armazenada deve possuir um ID único, este ID é retornado pelo
     * método para informar a identificação desta rota.
     */
    public int getRoute( double lat_orig, double lon_orig, double lat_dest, double lon_dest ) {
        int rout_id = -1;
        Object resultado;
        try {
            String SQL = "select get_route("
                    + lat_orig + ", " 
                    + lon_orig + ", "
                    + lat_dest + ", "
                    + lon_dest + ")";
            
            resultado = getTDJC().getEntityManager().createNativeQuery(SQL).getSingleResult();
            System.out.println(SQL);
            if(resultado != null) {
                
                rout_id = Integer.valueOf(resultado.toString());
            }
        } catch (Exception ex) {
          
            System.err.println("ERRO | AcessoTrilhas | getRoute | " + ex.getMessage());
        }
        getTDJC().getEntityManager().close();
        return rout_id;
    }
    
    
    /*
     * Retorna para aplicação android os dados para auxiliar na exibição das trilhas
     * (latitude e longitude do centro da trilha, maior distância entre dois pontos da trilha)
     * O retorno é feito em um vetor de double, onde os dados são postos na ordem descrita acima.
     */
    public double[] getDadosAuxiliaresVisualizacao(int codigoTrilha) {
        double[] dadosAuxiliares = null;
        List<Object[]> listaResultado;
        Object[] resultado;
        try {
            String SQL =
                "select st_xmax(centro) as longitude,"
                    + "st_ymax(centro) as latitude,"
                    + "comprimento"
                + " from ( "
                    + "select st_maxdistance("
                                + "geometriaTotalKM,"
                                + "geometriaTotalKM"
                            + ")*100*1000 as comprimento,"
                            + "st_centroid(geometriaTotalKM) as centro"
                    + " from ("
                        + "select ST_Collect(geometria) as geometriaTotalKM"
                        + " from trilha"
                        + " where codt = " + codigoTrilha
                        + " group by codt"
                    + ") as consultaGeometriaTotal"
                + ") as consultaDistanciaECentro;";
            listaResultado = getTDJC().getEntityManager().createNativeQuery(SQL).getResultList();
            if(listaResultado.size() > 0) {
                dadosAuxiliares = new double[3];
                resultado = listaResultado.get(0);
                dadosAuxiliares[0] = Double.parseDouble(resultado[0].toString());
                dadosAuxiliares[1] = Double.parseDouble(resultado[1].toString());
                dadosAuxiliares[2] = Double.parseDouble(resultado[2].toString());
            }
        } catch (Exception ex) {
            System.err.println("ERRO | AcessoTrilhas | getDadosAuxiliaresVisualizacao | " + ex.getMessage());
        }
        getTDJC().getEntityManager().close();
        return dadosAuxiliares;
    }    
    
    public int getDataTrilha(int codt) {
        String SQL = "SELECT CAST( EXTRACT( epoch from data_cadastro ) AS INTEGER ) FROM trilhadados WHERE codt = " + codt + ";";
        Object resultado = getTDJC().getEntityManager().createNativeQuery(SQL).getSingleResult();
        if(resultado != null) {
            return Integer.parseInt( resultado.toString() );
        } else {
            return 0;
        }
    }
    
    /*
     * Retorna informações não geográficas da trilha
     */
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

            for (int cont = 0; cont < listaResultado.size(); cont++) {
                bairros.add(Integer.parseInt(String.valueOf(listaResultado.get(cont))));
            }

            ng.setBairros(bairros);
            String SQL3 = "SELECT reg_cod FROM regiaotrilha WHERE codt = " + codigo + ";";
            listaResultado = getTDJC().getEntityManager().createNativeQuery(SQL3).getResultList();

            for (int cont = 0; cont < listaResultado.size(); cont++) {
                regioes.add(Integer.parseInt(String.valueOf(listaResultado.get(cont))));
            }

            ng.setRegioes(regioes);

            String SQL4 = "SELECT sup_cod FROM superficietrilha WHERE codt = " + codigo + ";";
            listaResultado = getTDJC().getEntityManager().createNativeQuery(SQL4).getResultList();

            for (int cont = 0; cont < listaResultado.size(); cont++) {
                seperficies.add(Integer.parseInt(String.valueOf(listaResultado.get(cont))));
            }

            ng.setSuperficies(seperficies);
            getTDJC().getEntityManager().close();
            return listaNG;

        } catch (Exception ex) {
            System.err.println("ERRO: " + ex.getMessage());
            getTDJC().getEntityManager().close();
        }

        return null;

    }
    
    
    
     public List<NaoGeografico> getTrilhaUser(int codigo, String email) {

        List<Object[]> listaResultado = null;
        Object[] resultado = null;

        List<NaoGeografico> listaNG = new ArrayList<NaoGeografico>();
        List<Integer> bairros = new ArrayList<Integer>();
        List<Integer> regioes = new ArrayList<Integer>();
        List<Integer> seperficies = new ArrayList<Integer>();

        Usuario usu;
            usu = (Usuario) getTDJC().getEntityManager().createNamedQuery("Usuario.findByUsuLogin").setParameter("usuEmail", email).getSingleResult();
            
        try {
            NaoGeografico ng = new NaoGeografico();
            String SQL = "SELECT comprimento_km, desnivel, nome, descricao, tip_cod, dif_cod, cid_cod FROM trilhadados "
                    + " INNER JOIN usuariotrilha ON trilhadados.codt=usuariotrilha.codt "
                    + " AND trilhadados.codt="+codigo
                    + " AND usuariotrilha.usu_cod="+usu.getUsuCod()+";";
            
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

            for (int cont = 0; cont < listaResultado.size(); cont++) {
                bairros.add(Integer.parseInt(String.valueOf(listaResultado.get(cont))));
            }

            ng.setBairros(bairros);
            String SQL3 = "SELECT reg_cod FROM regiaotrilha WHERE codt = " + codigo + ";";
            listaResultado = getTDJC().getEntityManager().createNativeQuery(SQL3).getResultList();

            for (int cont = 0; cont < listaResultado.size(); cont++) {
                regioes.add(Integer.parseInt(String.valueOf(listaResultado.get(cont))));
            }

            ng.setRegioes(regioes);

            String SQL4 = "SELECT sup_cod FROM superficietrilha WHERE codt = " + codigo + ";";
            listaResultado = getTDJC().getEntityManager().createNativeQuery(SQL4).getResultList();

            for (int cont = 0; cont < listaResultado.size(); cont++) {
                seperficies.add(Integer.parseInt(String.valueOf(listaResultado.get(cont))));
            }

            ng.setSuperficies(seperficies);
            getTDJC().getEntityManager().close();
            return listaNG;

        } catch (Exception ex) {
            System.err.println("ERRO: " + ex.getMessage());
            getTDJC().getEntityManager().close();
        }

        return null;

    }

    public byte[] getKMLFile(int codtr) {
        String textoKML; 
        try {
            String SQL = "SELECT ST_ASKML( ST_COLLECT( geometria ) ) FROM trilha WHERE codt = " + codtr + " GROUP BY codt";
            textoKML = String.valueOf( getTDJC().getEntityManager().createNativeQuery(SQL).getSingleResult() );
            getTDJC().getEntityManager().close();
        } catch (Exception ex) {
            getTDJC().getEntityManager().close();
            System.out.println("ERROR: " + ex.getMessage());
            return null;
        }
        //remover o "<MultiGeometry>" e adicionar as tags <kml>
        textoKML = "<kml>" + textoKML.substring(15, textoKML.length() - 16) + "</kml>"; 
        try {
            File arquivoKML = File.createTempFile("kmlTemporario", ".kml");
            BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoKML));
            writer.write(textoKML);
            writer.close();
            byte[] retorno = com.google.common.io.Files.toByteArray(arquivoKML);
            arquivoKML.delete();
            return retorno;
        } catch (Exception ex) {
            Logger.getLogger(AcessoTrilhas.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /*
     * analisarArquivoTri: Analisa um arquivo e retorna informações sobre os pontos da primeira trilha do arquivo.
     */
    public static ArrayList<String> analisarArquivoTri(File arq) {
        String p1, p2, linha = "", infoTrilha = "", retorno = "", trilha = "", temps = "";
        int temp, maior = -100, menor = 100, tottr = 0, num_e;
        String resto;
        BufferedReader br;
        ArrayList<String> linhatrack = new ArrayList<String>();
        ArrayList<String> temptrack = new ArrayList<String>();
        ArrayList<String> partes = new ArrayList<String>();

        try {
            br = new BufferedReader(new FileReader(arq));
            //Contar qtas trilhas tem no arquivo e pegar os seus dados:
            while ((linha = br.readLine()) != null) { //Para cada linha do arquivo...
                if (linha.startsWith("Track") && !linha.startsWith("Trackpoint")) { //se for o início de uma nova trilha...
                    tottr++;
                    if (tottr > 1) {
                        retorno = "O arquivo txt deve possuir apenas uma trilha!";
                    }
                    infoTrilha = linha.substring(6); //Guarda a linha atual
                } else if (linha.startsWith("Trackpoint")) { //Se for um ponto da trilha atual...
                    linha = linha.substring(linha.indexOf('\t') + 1); //Remove da linha o "Trackpoint"
                    p1 = linha.substring(0, 10).replace('S', '-');
                    resto = ("" + ((Float.parseFloat(p1.substring(4))) / 60)); //7.833333333333333E-4 => 0.0007833333...
                    resto = resto.replace(".", ""); //7833333333333333E-4
                    p1 = p1.substring(0, 3) + ".";
                    if (resto.contains("E")) {
                        num_e = Integer.parseInt(resto.substring(resto.indexOf("-") + 1));
                        for (int o = 0; o < num_e; o++) {
                            p1 = p1 + "0";
                        }
                        p1 = p1 + resto.substring(0, resto.indexOf("E"));
                    } else { //mesmo que 'if (resto.startsWith("0")){'
                        p1 = p1 + resto.substring(1);
                    }

                    p2 = linha.substring(11, linha.indexOf('\t')).replace('W', '-');
                    resto = ("" + ((Float.parseFloat(p2.substring(4))) / 60));
                    resto = resto.replace(".", "");
                    p2 = p2.substring(0, 3) + ".";
                    if (resto.contains("E")) {
                        num_e = Integer.parseInt(resto.substring(resto.indexOf("-") + 1));
                        for (int o = 0; o < num_e; o++) {
                            p2 = p2 + "0";
                        }
                        p2 = p2 + resto.substring(0, resto.indexOf("E"));
                    } else { //mesmo que 'if (resto.startsWith("0")){'
                        p2 = p2 + resto.substring(1);
                    }

                    linhatrack.add(p2 + " " + p1); //Guarda a coordenada do ponto
                    linha = linha.substring(linha.indexOf('\t') + 1); //Remove da linha a coordenada do ponto da trilha
                    linha = linha.substring(linha.indexOf('\t') + 1); //Remove da linha o data/hora que o ponto foi gravado
                    if (!linha.startsWith("\t")) { //Se o arquivo tiver a altura do ponto, pegá-lo para calcular o desnível
                        temp = Integer.parseInt(linha.substring(0, linha.indexOf(' ')));
                        if (temp > maior) {
                            maior = temp;
                        }
                        if (temp < menor) {
                            menor = temp;
                        }
                    }
                }
            }
            br.close();
            if (tottr == 0) {
                retorno = "Não há trilhas nesse arquivo!";
            }

            //Se tudo correu bem:
            infoTrilha = infoTrilha.substring(infoTrilha.indexOf('\t') + 1, infoTrilha.length());
            infoTrilha = infoTrilha.substring(infoTrilha.indexOf('\t') + 1, infoTrilha.length()); //remove "Start Time"
            infoTrilha = infoTrilha.substring(infoTrilha.indexOf('\t') + 1, infoTrilha.length()); //remove "Elapsed Time"
            if (infoTrilha.substring(0, infoTrilha.indexOf('\t') + 1).contains("km")) {
            } else {
            }

            //Se trilha tiver + de 163 pontos, dividí-la no menor número possível de partes
            //Parte 1: Remove pontos duplicados
            temps = linhatrack.get(0);
            temptrack.add(temps);
            for (int i = 1; i < linhatrack.size(); i++) {
                if (!linhatrack.get(i).equals(temps)) { //Se o próximo ponto não for igual ao anterior
                    temps = linhatrack.get(i);
                    temptrack.add(temps);
                }
            }
            //Parte 2: Dividir a trilha em partes de 163 pontos
            int numPartes; //numero de partes em que a trilha será dividida
            if (temptrack.size() <= 163) {
                numPartes = 1;
            } else {
                numPartes = (int) (temptrack.size() / 163) + 1;
            }
            for (int i = 0; i < numPartes; i++) {
                for (int j = 0 + ((163 * i) - i); j < 163 + ((163 * i) - i); j++) {
                    try {
                        trilha = trilha + temptrack.get(j) + ",\n";
                    } catch (Exception e) {
                    } //Só pro programa não fechar
                }
                trilha = trilha.substring(0, trilha.length() - 2);
                partes.add(trilha);
                trilha = "";
            }

        } catch (Exception ex) {
            retorno = ex.toString();
        }
        return partes;
    }
}
