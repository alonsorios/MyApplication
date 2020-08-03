
package com.alonsorios.myapplication.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestVinculo {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("codigoVincula")
    @Expose
    private String codigoVincula;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RequestVinculo() {
    }

    /**
     * 
     * @param codigoVincula
     * @param username
     */
    public RequestVinculo(String username, String codigoVincula) {
        super();
        this.username = username;
        this.codigoVincula = codigoVincula;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCodigoVincula() {
        return codigoVincula;
    }

    public void setCodigoVincula(String codigoVincula) {
        this.codigoVincula = codigoVincula;
    }

}
