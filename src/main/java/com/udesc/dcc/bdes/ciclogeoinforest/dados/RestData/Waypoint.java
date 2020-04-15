/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.udesc.dcc.bdes.ciclogeoinforest.dados.RestData;

import java.util.ArrayList;

/**
 *
 * @author endrew
 */
public class Waypoint {
    
    private int codt;
    private String descricao;
    private String nome;
    private String geometria;
    private ArrayList<byte[]> imagens;
    private ArrayList<Integer> categorias;
    private String login;
    private String senha;

    public Waypoint(int codt, String descricao, String nome, String geometria, ArrayList<byte[]> imagens, ArrayList<Integer> categorias, String login, String senha) {
        this.codt = codt;
        this.descricao = descricao;
        this.nome = nome;
        this.geometria = geometria;
        this.imagens = imagens;
        this.categorias = categorias;
        this.login = login;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCodt() {
        return codt;
    }

    public void setCodt(int codt) {
        this.codt = codt;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getGeometria() {
        return geometria;
    }

    public void setGeometria(String geometria) {
        this.geometria = geometria;
    }

    public ArrayList<byte[]> getImagens() {
        return imagens;
    }

    public void setImagens(ArrayList<byte[]> imagens) {
        this.imagens = imagens;
    }

    public ArrayList<Integer> getCategorias() {
        return categorias;
    }

    public void setCategorias(ArrayList<Integer> categorias) {
        this.categorias = categorias;
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
    
    
    
}
