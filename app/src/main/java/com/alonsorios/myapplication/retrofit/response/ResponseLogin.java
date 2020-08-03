package com.alonsorios.myapplication.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLogin {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("photourl")
    @Expose
    private String photourl;
    @SerializedName("contrasena")
    @Expose
    private String contrasena;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResponseLogin() {
    }

    /**
     *
     * @param photourl
     * @param contrasena
     * @param username
     */
    public ResponseLogin(String username, String photourl, String contrasena) {
        super();
        this.username = username;
        this.photourl = photourl;
        this.contrasena = contrasena;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

}