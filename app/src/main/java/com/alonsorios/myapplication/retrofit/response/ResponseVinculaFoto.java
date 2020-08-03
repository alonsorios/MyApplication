
package com.alonsorios.myapplication.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseVinculaFoto {

    @SerializedName("imagen")
    @Expose
    private String imagen;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponseVinculaFoto() {
    }

    /**
     * 
     * @param imagen
     */
    public ResponseVinculaFoto(String imagen) {
        super();
        this.imagen = imagen;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

}
