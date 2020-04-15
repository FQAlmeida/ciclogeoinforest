package com.udesc.dcc.bdes.ciclogeoinforest.negocio;

import com.udesc.dcc.bdes.ciclogeoinforest.dados.AcessoBairros;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.AcessoCidades;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.AcessoTrilhas;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.AcessoWaypoints;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.*;
import java.io.FileReader;
import java.util.ArrayList;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Leandro G. Destro
 */
public class Edicao {

    private EntityManagerFactory emf;
    public AcessoUsuarios u;
    
    public Edicao() {
        emf = Persistence.createEntityManagerFactory("PU");  
        u = new AcessoUsuarios(emf);
    }
    
    public boolean setCapabilities() {
        return false;
    }

    public boolean setMap() {
        return false;
    }

    public boolean setFeatureInfo() {
        return false;
    }

    public boolean newCapabilities() {
        return false;
    }

    public boolean newMap() {
        return false;
    }

    public boolean newFeatureInfo() {
        return false;
    }

    public boolean deleteCapabilities() {
        return false;
    }

    public boolean deleteMap() {
        return false;
    }

    public boolean deleteFeatureInfo() {
        return false;
    }


    public boolean insertBairro(String nome, int cid_cod) {
        AcessoBairros b = new AcessoBairros(emf);
        return b.newBairro(nome, cid_cod);
    }
    
    public boolean updateBairro(int baiCod, String baiNome, int cidCod) {
        AcessoBairros b = new AcessoBairros(emf);
        return b.updateBairro(new Bairro(baiCod, baiNome, cidCod));
    }
    
    public boolean deleteBairro(int bai_cod) {
        AcessoBairros b = new AcessoBairros(emf);
        return b.deleteBairro(bai_cod);
    }
    
    public Bairro findBairro(int idBairro){
        AcessoBairros b = new AcessoBairros(emf);
        return b.findBairro(idBairro);
    }
    
    public boolean insertCidade(String nome, int est_cod) {
        AcessoCidades c = new AcessoCidades(emf);
        return c.newCidade(nome, est_cod);
    }
    
    public boolean updateCidade(int cidCod, String cidNome, int estCod) {
        AcessoCidades c = new AcessoCidades(emf);
        return c.updateCidade(new Cidade(cidCod, cidNome, new Estado(estCod)));
    }
    
    public boolean deleteCidade(int cid_cod) {
        AcessoCidades c = new AcessoCidades(emf);
        return c.deleteCidade(cid_cod);
    }
    
    public Cidade findCidade(int idCidade){
        AcessoCidades c = new AcessoCidades(emf);
        return c.findCidade(idCidade);
    }
    
    //Inserção de waypoints com o arquivo geográfico já interpretado
    public boolean insertWaypoint(int codt, String descricao, String nome, String geometria, int usu_cod,
            ArrayList<byte[]> imagens, ArrayList<Integer> categorias){
        
        AcessoWaypoints w = new AcessoWaypoints(emf);
        return w.newWaypoint(codt, descricao, nome, geometria, usu_cod, imagens, categorias);
    }
    
    /*
    //Inserção de waypoints com o arquivo geográfico já interpretado
    public boolean insertWaypoint(int codt, String descricao, String nome, byte[] geometria, int usu_cod,
            ArrayList<byte[]> imagens, ArrayList<Integer> categorias){
        
        AcessoWaypoints w = new AcessoWaypoints();
        return w.newWaypoint(codt, descricao, nome, geometria, usu_cod, imagens, categorias);
    }
    */
    
    public boolean deleteWaypoint(int waypointCodwp){
        AcessoWaypoints w = new AcessoWaypoints(emf);
        return w.deleteWaypoint(waypointCodwp);
    }
    
    public Waypoint findWaypoint(int idWaypoint){
        AcessoWaypoints w = new AcessoWaypoints(emf);
        return w.findWaypoint(idWaypoint);
    }
    
    //Inserção de trilhas com o arquivo geográfico já interpretado
    public boolean insertTrilha(float comprimento, int desnivel, String nome,
            String descricao,byte[] graf_altitude,
            int tip_cod, int dif_cod, int cid_cod, int usu_cod,
            ArrayList<String> geometria, ArrayList<Integer> bairros,
            ArrayList<Integer> regioes, ArrayList<Integer> superficies){
        
        AcessoTrilhas t = new AcessoTrilhas(emf);
        return t.newTrilha(comprimento, desnivel, nome, descricao,graf_altitude, tip_cod, dif_cod, cid_cod, usu_cod, geometria, bairros, regioes, superficies);
    }
    
    //Inserção de trilhas com o arquivo geográfico já interpretado
    public boolean insertTrilha(FileReader arquivo_trilha,byte[] graf_altitude,
            int tip_cod, int dif_cod, int cid_cod, int usu_cod,
            String descricao, ArrayList<Integer> bairros,
            ArrayList<Integer> regioes, ArrayList<Integer> superficies){
        
        AcessoTrilhas t = new AcessoTrilhas(emf);
        return t.newTrilha(arquivo_trilha,graf_altitude,
                tip_cod, dif_cod, cid_cod, usu_cod, descricao,
                bairros, regioes, superficies);
        
    }
    
     //Inserção de trilhas recebida do android
    public int insertTrilha(String comprimento, int desnivel, String nome,
            String descricao,int tip_cod, int dif_cod, int cid_cod, int usu_cod,
            ArrayList<String> geometria, ArrayList<Integer> bairros,
            ArrayList<Integer> regioes, ArrayList<Integer> superficies,
            String email, String timestampString, byte[] graficoAltitude, ArrayList<Double> altitudes){
        
        AcessoTrilhas t = new AcessoTrilhas(emf);
        
        return t.newTrilha(comprimento, desnivel, nome, descricao, tip_cod,
                dif_cod, cid_cod, usu_cod, geometria, bairros, regioes,
                superficies, email, timestampString, graficoAltitude, altitudes);
    }
    
    public boolean deleteTrilha(int trilhadadosCodt){
        AcessoTrilhas t = new AcessoTrilhas(emf);
        return t.deleteTrilha(trilhadadosCodt);
    }
    
    public Trilhadados findTrilha(int idTrilhadados){
        AcessoTrilhas t = new AcessoTrilhas(emf);
        return t.findTrilha(idTrilhadados);
    }
    
    public boolean insertUsuario(String email, String nome, String senha){
        return u.newUsuario(email, nome, senha);
    }
    
    public boolean updateUsuario(Usuario usu, String oldLogin, String oldSenha) {
        return u.updateUsuario(usu, oldLogin, oldSenha);
    }

    public boolean deleteUsuario(Usuario usu) {
        return u.deleteUsuario(usu);
    }

}