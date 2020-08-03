
package com.alonsorios.myapplication.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponsePerfil {

    @SerializedName("correo")
    @Expose
    private String correo;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("NombrePaciente")
    @Expose
    private String nombrePaciente;
    @SerializedName("ApellidoPaciente")
    @Expose
    private String apellidoPaciente;
    @SerializedName("Peso")
    @Expose
    private String peso;
    @SerializedName("Sexo")
    @Expose
    private String sexo;
    @SerializedName("MedicoAsociado")
    @Expose
    private String medicoAsociado;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponsePerfil() {
    }

    /**
     * 
     * @param peso
     * @param correo
     * @param apellidoPaciente
     * @param nombrePaciente
     * @param sexo
     * @param medicoAsociado
     * @param username
     */
    public ResponsePerfil(String correo, String username, String nombrePaciente, String apellidoPaciente, String peso, String sexo, String medicoAsociado) {
        super();
        this.correo = correo;
        this.username = username;
        this.nombrePaciente = nombrePaciente;
        this.apellidoPaciente = apellidoPaciente;
        this.peso = peso;
        this.sexo = sexo;
        this.medicoAsociado = medicoAsociado;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getApellidoPaciente() {
        return apellidoPaciente;
    }

    public void setApellidoPaciente(String apellidoPaciente) {
        this.apellidoPaciente = apellidoPaciente;
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

    public String getMedicoAsociado() {
        return medicoAsociado;
    }

    public void setMedicoAsociado(String medicoAsociado) {
        this.medicoAsociado = medicoAsociado;
    }

}
