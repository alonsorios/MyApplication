
package com.alonsorios.myapplication.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponsePrincipal {

    @SerializedName("promedio")
    @Expose
    private String promedio;
    @SerializedName("ultimo")
    @Expose
    private String ultimo;
    @SerializedName("fecha_ultimo")
    @Expose
    private String fechaUltimo;
    @SerializedName("num_muestras")
    @Expose
    private String numMuestras;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponsePrincipal() {
    }

    /**
     * 
     * @param ultimo
     * @param fechaUltimo
     * @param promedio
     * @param numMuestras
     */
    public ResponsePrincipal(String promedio, String ultimo, String fechaUltimo, String numMuestras) {
        super();
        this.promedio = promedio;
        this.ultimo = ultimo;
        this.fechaUltimo = fechaUltimo;
        this.numMuestras = numMuestras;
    }

    public String getPromedio() {
        return promedio;
    }

    public void setPromedio(String promedio) {
        this.promedio = promedio;
    }

    public String getUltimo() {
        return ultimo;
    }

    public void setUltimo(String ultimo) {
        this.ultimo = ultimo;
    }

    public String getFechaUltimo() {
        return fechaUltimo;
    }

    public void setFechaUltimo(String fechaUltimo) {
        this.fechaUltimo = fechaUltimo;
    }

    public String getNumMuestras() {
        return numMuestras;
    }

    public void setNumMuestras(String numMuestras) {
        this.numMuestras = numMuestras;
    }

}
