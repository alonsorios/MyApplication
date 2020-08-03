
package com.alonsorios.myapplication.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseFotoPerfil {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("url")
    @Expose
    private String url;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponseFotoPerfil() {
    }

    /**
     * 
     * @param url
     * @param username
     */
    public ResponseFotoPerfil(String username, String url) {
        super();
        this.username = username;
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
