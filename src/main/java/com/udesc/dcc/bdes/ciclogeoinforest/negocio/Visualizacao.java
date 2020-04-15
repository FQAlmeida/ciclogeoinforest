package com.udesc.dcc.bdes.ciclogeoinforest.negocio;


import com.udesc.dcc.bdes.ciclogeoinforest.dados.AcessoBairros;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.AcessoCategorias;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.AcessoCidades;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.DadosLayer;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.AcessoDificuldades;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.FeatureInfo;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.NaoGeografico;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.AcessoRegioes;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.AcessoSuperficies;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.AcessoTipos;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.AcessoTrilhas;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.AcessoWaypoints;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Bairro;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Categoria;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Cidade;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Dificuldade;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Regiao;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Superficie;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Tipo;
import java.awt.Image;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Leandro Lisura
 */
public class Visualizacao {
    
    private EntityManagerFactory emf;
    
    public Visualizacao(){
        emf = Persistence.createEntityManagerFactory("PU");
    }
    
    public List<Bairro> getBairros() {    
        AcessoBairros bairro = new AcessoBairros(emf);;
        List<Bairro> lista = bairro.getBairros();
        return lista;
    }
    
    public List<Cidade> getCidades() {
        AcessoCidades cidades = new AcessoCidades(emf);;
        List<Cidade> lista = cidades.getCidades();
        return lista;
    }
    
    public List<Dificuldade> getDificuldades() {
        AcessoDificuldades dificuldades = new AcessoDificuldades(emf);
        List<Dificuldade> lista = dificuldades.getDificuldades();
        return lista;
    }
    
    public List<Superficie> getSuperficies() {
        AcessoSuperficies superficies = new AcessoSuperficies(emf);
        List<Superficie> lista = superficies.getSuperficies();
        return lista;
    }
    
    public List<Regiao> getRegioes() {
        AcessoRegioes regioes = new AcessoRegioes(emf);
        List<Regiao> lista = regioes.getRegioes();
        return lista;
    }
    
    public List<Categoria> getCategorias() {
        AcessoCategorias categoria = new AcessoCategorias(emf);
        List<Categoria> lista = categoria.getCategorias();
        return lista;
    }
    
    public List<Tipo> getTipos() {
        AcessoTipos tipos = new AcessoTipos(emf);
        List<Tipo> lista = tipos.getTipos();
        return lista;
    }
    
    public List<DadosLayer> downloadLayer(List<Integer> codt){
        AcessoWaypoints wp = new AcessoWaypoints(emf);
        List<DadosLayer> lista = wp.downloadLayer(codt);
        return lista;
    }
    
    public int getDataTrilha(Integer codt){
        AcessoTrilhas trilhas = new AcessoTrilhas(emf);
        return trilhas.getDataTrilha(codt);
    }
    
    public java.util.List<NaoGeografico> getNaoGeografico(String tipo, int cod) {
        
        AcessoWaypoints wp = new AcessoWaypoints(emf);
        AcessoTrilhas trilhas = new AcessoTrilhas(emf);
        
        if (tipo.equals("waypoint")) {
            return wp.getNaoGeograficoWayPoint(cod);
        } else {
            if (tipo.equals("trilha")) {
                return trilhas.getNaoGeograficoTrilha(cod);
            } else {
                return null;
            }
        }
    }
    
     public java.util.List<NaoGeografico> getTrilhaUser(String email, int cod) {         
        AcessoTrilhas trilhas = new AcessoTrilhas(emf);
        return trilhas.getTrilhaUser(cod, email);
    }

      public List<FeatureInfo> getTrilhasUser(String email){        
         AcessoWaypoints wp = new AcessoWaypoints(emf);         
         return wp.getTrilhasUser(email);
     }
      
         public List<FeatureInfo> getWaypointsUser(String email){        
         AcessoWaypoints wp = new AcessoWaypoints(emf);         
         return wp.getWaypointsUser(email);
     }
    
     public java.util.List<NaoGeografico> getWaypointUser(int cod) {         
        return getNaoGeografico("waypoint",cod);
    }
         
     public List<FeatureInfo> buscaTrilhas(List<Integer> tipo, List<Integer> dificuldade, 
            List<Integer> bairro, List<Integer> regiao, List<Integer> superficie, List<Integer> categoria){
        
         AcessoWaypoints wp = new AcessoWaypoints(emf);
         
         List<FeatureInfo> lista = wp.buscaTrilhas(tipo, dificuldade, bairro, regiao, superficie, categoria);
        return lista;
     }
     
     public java.util.List<FeatureInfo> getFeatureInfo(double bbox1, double bbox2, double bbox3, double bbox4, int x, int y, int srs, int widthh, int heightt) {
         
         AcessoWaypoints wp = new AcessoWaypoints(emf);
         
         List<FeatureInfo> lista = wp.getFeatureInfo(bbox1, bbox2, bbox3, bbox4, x, y, srs, widthh, heightt);
         return lista;
     }
     
     public int getRoute( double lat_orig, double lon_orig, double lat_dest, double lon_dest ){
         AcessoTrilhas acessoTrilhas = new AcessoTrilhas(emf);
         int route_id = acessoTrilhas.getRoute( lat_orig, lon_orig, lat_dest, lon_dest );
         System.out.println("chegou aki");
         return route_id;
     }
     
     public double[] getDadosAuxiliaresVisualizacao(int codigoTrilha){
         AcessoTrilhas acessoTrilhas = new AcessoTrilhas(emf);
         double[] dadosAuxiliares = acessoTrilhas.getDadosAuxiliaresVisualizacao(codigoTrilha);
         return dadosAuxiliares;
     }
     
     public java.util.List<FeatureInfo> getFeatureInfoProximo(double latitude, double longitude) {
         AcessoWaypoints wp = new AcessoWaypoints(emf);
         List<FeatureInfo> lista = wp.getFeatureInfoProximo(latitude,longitude);
         return lista;
     }
     
     public byte[] getByteImage(int codimg, int codwp){
         AcessoWaypoints wp = new AcessoWaypoints(emf);
         byte[] imagem = wp.getByteImage(codimg, codwp);
         return imagem;
     }
     
     public Image getImage(int codimg, int codwp){
         AcessoWaypoints wp = new AcessoWaypoints(emf);
         Image imagem = wp.getImage(codimg, codwp);
         return imagem;
     }
     
     public byte[] getKMLFile(int codtr){
         AcessoTrilhas trilhas = new AcessoTrilhas(emf);
         return trilhas.getKMLFile(codtr);
     }
     
     public Image getMap(int srs, boolean transparent, boolean tiled, double bbox1, double bbox2, double bbox3, double bbox4, int widthh,int heightt, List<Integer> cql_filter) {
         AcessoTrilhas trilhas = new AcessoTrilhas(emf);
         /*byte[] trilha = */return trilhas.getMap(srs, transparent, tiled, bbox1, bbox2, bbox3, bbox4, widthh, heightt, cql_filter);
         //return trilha;
     }
     
}
