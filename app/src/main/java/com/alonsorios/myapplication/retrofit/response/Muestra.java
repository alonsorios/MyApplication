
package com.alonsorios.myapplication.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Muestra {

    @SerializedName("idMuestra")
    @Expose
    private String idMuestra;
    @SerializedName("ValorGlucosa")
    @Expose
    private String valorGlucosa;
    @SerializedName("FechaMuestra")
    @Expose
    private String fechaMuestra;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Muestra() {
    }

    public Muestra(Muestra nuevoMuestra) {
        this.idMuestra =nuevoMuestra.getIdMuestra();
        this.valorGlucosa = nuevoMuestra.getValorGlucosa();
        this.fechaMuestra = nuevoMuestra.getFechaMuestra();
    }

    /**
     * 
     * @param idMuestra
     * @param fechaMuestra
     * @param valorGlucosa
     */
    public Muestra(String idMuestra, String valorGlucosa, String fechaMuestra) {
        super();
        this.idMuestra = idMuestra;
        this.valorGlucosa = valorGlucosa;
        this.fechaMuestra = fechaMuestra;
    }

    public String getIdMuestra() {
        return idMuestra;
    }

    public void setIdMuestra(String idMuestra) {
        this.idMuestra = idMuestra;
    }

    public String getValorGlucosa() {
        return valorGlucosa;
    }

    public void setValorGlucosa(String valorGlucosa) {
        this.valorGlucosa = valorGlucosa;
    }

    public String getFechaMuestra() {
        return fechaMuestra;
    }

    public void setFechaMuestra(String fechaMuestra) {
        this.fechaMuestra = fechaMuestra;
    }

}
