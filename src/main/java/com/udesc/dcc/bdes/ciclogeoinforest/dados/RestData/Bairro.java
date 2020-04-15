/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.udesc.dcc.bdes.ciclogeoinforest.dados.RestData;

/**
 *
 * @author endrew
 */
public class Bairro {
    
    private String nome;
    private int cid_cod;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCid_cod() {
        return cid_cod;
    }

    public void setCid_cod(int cid_cod) {
        this.cid_cod = cid_cod;
    }

    public Bairro(String nome, int cid_cod) {
        this.nome = nome;
        this.cid_cod = cid_cod;
    }
    
    
    
}
