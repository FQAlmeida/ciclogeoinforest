/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects;

/**
 *
 * @author Leandro Lisura
 */
public class FeatureInfo {
    private String tipo;
    private int cod;
    private String nome;
    private int CodtFromWaypoint;

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
     * @return the CodtFromWaypoint
     */
    public int getCodtFromWaypoint() {
        return CodtFromWaypoint;
    }

    /**
     * @param CodtFromWaypoint the CodtFromWaypoint to set
     */
    public void setCodtFromWaypoint(int CodtFromWaypoint) {
        this.CodtFromWaypoint = CodtFromWaypoint;
    }

    
    
}
