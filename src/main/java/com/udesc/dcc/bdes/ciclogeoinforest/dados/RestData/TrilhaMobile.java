/*
 * To change this license header; choose License Headers in Project Properties.
 * To change this template file; choose Tools | Templates
 * and open the template in the editor.
 */
package com.udesc.dcc.bdes.ciclogeoinforest.dados.RestData;

import java.util.ArrayList;

/**
 *
 * @author endrew
 */
public class TrilhaMobile {
    private String comprimento; 
    private int desnivel; 
    private String nome; 
    private String descricao; 
    private int tip_cod; 
    private int dif_cod; 
    private int cid_cod; 
    private ArrayList<String> geometria; 
    private ArrayList<Integer> bairros; 
    private ArrayList<Integer> regioes; 
    private ArrayList<Integer> superficies; 
    private String login; 
    private String senha; 
    private String timestampString; 
    private byte[] graficoAltitude; 
    private String altitudes;

    public TrilhaMobile(String comprimento, int desnivel, String nome, String descricao, int tip_cod, int dif_cod, int cid_cod, ArrayList<String> geometria, ArrayList<Integer> bairros, ArrayList<Integer> regioes, ArrayList<Integer> superficies, String login, String senha, String timestampString, byte[] graficoAltitude, String altitudes) {
        this.comprimento = comprimento;
        this.desnivel = desnivel;
        this.nome = nome;
        this.descricao = descricao;
        this.tip_cod = tip_cod;
        this.dif_cod = dif_cod;
        this.cid_cod = cid_cod;
        this.geometria = geometria;
        this.bairros = bairros;
        this.regioes = regioes;
        this.superficies = superficies;
        this.login = login;
        this.senha = senha;
        this.timestampString = timestampString;
        this.graficoAltitude = graficoAltitude;
        this.altitudes = altitudes;
    }

    public String getComprimento() {
        return comprimento;
    }

    public void setComprimento(String comprimento) {
        this.comprimento = comprimento;
    }

    public int getDesnivel() {
        return desnivel;
    }

    public void setDesnivel(int desnivel) {
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

    public int getTip_cod() {
        return tip_cod;
    }

    public void setTip_cod(int tip_cod) {
        this.tip_cod = tip_cod;
    }

    public int getDif_cod() {
        return dif_cod;
    }

    public void setDif_cod(int dif_cod) {
        this.dif_cod = dif_cod;
    }

    public int getCid_cod() {
        return cid_cod;
    }

    public void setCid_cod(int cid_cod) {
        this.cid_cod = cid_cod;
    }

    public ArrayList<String> getGeometria() {
        return geometria;
    }

    public void setGeometria(ArrayList<String> geometria) {
        this.geometria = geometria;
    }

    public ArrayList<Integer> getBairros() {
        return bairros;
    }

    public void setBairros(ArrayList<Integer> bairros) {
        this.bairros = bairros;
    }

    public ArrayList<Integer> getRegioes() {
        return regioes;
    }

    public void setRegioes(ArrayList<Integer> regioes) {
        this.regioes = regioes;
    }

    public ArrayList<Integer> getSuperficies() {
        return superficies;
    }

    public void setSuperficies(ArrayList<Integer> superficies) {
        this.superficies = superficies;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTimestampString() {
        return timestampString;
    }

    public void setTimestampString(String timestampString) {
        this.timestampString = timestampString;
    }

    public byte[] getGraficoAltitude() {
        return graficoAltitude;
    }

    public void setGraficoAltitude(byte[] graficoAltitude) {
        this.graficoAltitude = graficoAltitude;
    }

    public String getAltitudes() {
        return altitudes;
    }

    public void setAltitudes(String altitudes) {
        this.altitudes = altitudes;
    }
}
