
package com.alonsorios.myapplication.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestLogin {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("contrasena")
    @Expose
    private String contrasena;

    /**
     * No args constructor for use in serialization
     *
     */
    public RequestLogin() {
    }

    /**
     *
     * @param contrasena
     * @param username
     */
    public RequestLogin(String username, String contrasena) {
        super();
        this.username = username;
        this.contrasena = contrasena;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

}