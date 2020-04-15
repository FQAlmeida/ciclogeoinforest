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
public class UsuarioRest {
    
    private String email;
    
    private String nome;
    
    private String senha;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public UsuarioRest(String email, String nome, String senha) {
        this.email = email;
        this.nome = nome;
        this.senha = senha;
    }
    
}
