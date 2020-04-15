/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.udesc.dcc.bdes.ciclogeoinforest.dados.RestData;

/**
 *
 * @author endrew
 * 
 */
public class Cidade {
    
    private String nome;
    
    private int est_cod;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getEst_cod() {
        return est_cod;
    }

    public void setEst_cod(int est_cod) {
        this.est_cod = est_cod;
    }

    public Cidade(String nome, int est_cod) {
        this.nome = nome;
        this.est_cod = est_cod;
    }
    
}
