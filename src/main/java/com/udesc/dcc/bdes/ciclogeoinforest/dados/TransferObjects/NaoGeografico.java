/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Leandro Lisura
 */
public class NaoGeografico {
    private String tipo;
    //se for waypoint
    private String descricao;
    private int cod;
    private String nome;
    //se for trilha
    private double comprimento;
    private double desnivel;
    private int tip_cod;
    private int dif_cod;
    private int cid_cod;
    
    private List<Integer> bairros = new ArrayList<Integer>();
    private List<Integer> regioes = new ArrayList<Integer>();
    private List<Integer> superficies = new ArrayList<Integer>();
    
    private List<Integer> categoriaWaypoint = new ArrayList<Integer>();
    private int numeroDeImagens;

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the descrisao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descrisao the descrisao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the cod
     */
    public int getCod() {
        return cod;
    }

    /**
     * @param cod the cod to set
     */
    public void setCod(int cod) {
        this.cod = cod;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the comprimento
     */
    public double getComprimento() {
        return comprimento;
    }

    /**
     * @param comprimento the comprimento to set
     */
    public void setComprimento(double comprimento) {
        this.comprimento = comprimento;
    }

    /**
     * @return the desnivel
     */
    public double getDesnivel() {
        return desnivel;
    }

    /**
     * @param desnivel the desnivel to set
     */
    public void setDesnivel(double desnivel) {
        this.desnivel = desnivel;
    }

    /**
     * @return the tip_cod
     */
    public int getTip_cod() {
        return tip_cod;
    }

    /**
     * @param tip_cod the tip_cod to set
     */
    public void setTip_cod(int tip_cod) {
        this.tip_cod = tip_cod;
    }

    /**
     * @return the dif_cod
     */
    public int getDif_cod() {
        return dif_cod;
    }

    /**
     * @param dif_cod the dif_cod to set
     */
    public void setDif_cod(int dif_cod) {
        this.dif_cod = dif_cod;
    }

    /**
     * @return the cid_cod
     */
    public int getCid_cod() {
        return cid_cod;
    }

    /**
     * @param cid_cod the cid_cod to set
     */
    public void setCid_cod(int cid_cod) {
        this.cid_cod = cid_cod;
    }

    /**
     * @return the bairros
     */
    public List<Integer> getBairros() {
        return bairros;
    }

    /**
     * @param bairros the bairros to set
     */
    public void setBairros(List<Integer> bairros) {
        this.bairros = bairros;
    }

    /**
     * @return the regioes
     */
    public List<Integer> getRegioes() {
        return regioes;
    }

    /**
     * @param regioes the regioes to set
     */
    public void setRegioes(List<Integer> regioes) {
        this.regioes = regioes;
    }

    /**
     * @return the seperficies
     */
    public List<Integer> getSuperficies() {
        return superficies;
    }

    /**
     * @param seperficies the seperficies to set
     */
    public void setSuperficies(List<Integer> seperficies) {
        this.superficies = seperficies;
    }

    /**
     * @return the categoriaWaypoint
     */
    public List<Integer> getCategoriaWaypoint() {
        return categoriaWaypoint;
    }

    /**
     * @param categoriaWaypoint the categoriaWaypoint to set
     */
    public void setCategoriaWaypoint(List<Integer> categoriaWaypoint) {
        this.categoriaWaypoint = categoriaWaypoint;
    }

    /**
     * @return the numeroDeImagens
     */
    public int getNumeroDeImagens() {
        return numeroDeImagens;
    }

    /**
     * @param numeroDeImagens the numeroDeImagens to set
     */
    public void setNumeroDeImagens(int numeroDeImagens) {
        this.numeroDeImagens = numeroDeImagens;
    }
            
            
}
