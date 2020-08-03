
package com.alonsorios.myapplication.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestPerfil {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("apellidos")
    @Expose
    private String apellidos;
    @SerializedName("Peso")
    @Expose
    private String peso;
    @SerializedName("Sexo")
    @Expose
    private String sexo;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RequestPerfil() {
    }

    /**
     * 
     * @param apellidos
     * @param peso
     * @param sexo
     * @param nombre
     * @param username
     */
    public RequestPerfil(String username, String nombre, String apellidos, String peso, String sexo) {
        super();
        this.username = username;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.peso = peso;
        this.sexo = sexo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

}
