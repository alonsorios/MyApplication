
package com.alonsorios.myapplication.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestSignup {

    @SerializedName("correo")
    @Expose
    private String correo;
    @SerializedName("Nombre")
    @Expose
    private String nombre;
    @SerializedName("Apellidos")
    @Expose
    private String apellidos;
    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("Contrasena")
    @Expose
    private String contrasena;
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
    public RequestSignup() {
    }

    /**
     * 
     * @param apellidos
     * @param peso
     * @param correo
     * @param contrasena
     * @param sexo
     * @param nombre
     * @param username
     */
    public RequestSignup(String correo, String nombre, String apellidos, String username, String contrasena, String peso, String sexo) {
        super();
        this.correo = correo;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.username = username;
        this.contrasena = contrasena;
        this.peso = peso;
        this.sexo = sexo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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
