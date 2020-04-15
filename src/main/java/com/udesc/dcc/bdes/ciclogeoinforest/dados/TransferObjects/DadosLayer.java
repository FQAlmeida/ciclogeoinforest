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
public class DadosLayer {
    
    private List<Double> latitudeWaypoint = new ArrayList<Double>();
    private List<Double> longitudeWaypoint = new ArrayList<Double>();
    
    private List<Integer> codwp = new ArrayList<Integer>();
    
    private List<Double> latitudeTrilha = new ArrayList<Double>();
    private List<Double> longitudeTrilha = new ArrayList<Double>();

    /**
     * @return the latitudeWaypoint
     */
    public List<Double> getLatitudeWaypoint() {
        return latitudeWaypoint;
    }

    /**
     * @param latitudeWaypoint the latitudeWaypoint to set
     */
    public void setLatitudeWaypoint(List<Double> latitudeWaypoint) {
        this.latitudeWaypoint = latitudeWaypoint;
    }

    /**
     * @return the longitudeWaypoint
     */
    public List<Double> getLongitudeWaypoint() {
        return longitudeWaypoint;
    }

    /**
     * @param longitudeWaypoint the longitudeWaypoint to set
     */
    public void setLongitudeWaypoint(List<Double> longitudeWaypoint) {
        this.longitudeWaypoint = longitudeWaypoint;
    }

    /**
     * @return the codwp
     */
    public List<Integer> getCodwp() {
        return codwp;
    }

    /**
     * @param codwp the codwp to set
     */
    public void setCodwp(List<Integer> codwp) {
        this.codwp = codwp;
    }

    /**
     * @return the latitudeTrilha
     */
    public List<Double> getLatitudeTrilha() {
        return latitudeTrilha;
    }

    /**
     * @param latitudeTrilha the latitudeTrilha to set
     */
    public void setLatitudeTrilha(List<Double> latitudeTrilha) {
        this.latitudeTrilha = latitudeTrilha;
    }

    /**
     * @return the longitudeTrilha
     */
    public List<Double> getLongitudeTrilha() {
        return longitudeTrilha;
    }

    /**
     * @param longitudeTrilha the longitudeTrilha to set
     */
    public void setLongitudeTrilha(List<Double> longitudeTrilha) {
        this.longitudeTrilha = longitudeTrilha;
    }

    
}
