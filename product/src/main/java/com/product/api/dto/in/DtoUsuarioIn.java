package com.product.api.dto.in;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DtoUsuarioIn {

    @JsonProperty("nombres")
    @NotNull(message = "Los nombres son obligatorios")
    private String nombres;

    @JsonProperty("apellidos")
    @NotNull(message = "Los apellidos son obligatorios")
    private String apellidos;

    @JsonProperty("username")
    @NotNull(message = "El username es obligatorio")
    private String nombreUsuario;

    @JsonProperty("correo")
    @NotNull(message = "El correo es obligatorio")
    @Email
    private String correo;

    @JsonProperty("password")
    @NotNull(message = "La contraseña es obligatoria")
    @Size(min = 8)
    private String contraseña;

    public DtoUsuarioIn(@NotNull(message = "Los nombres son obligatorios") String nombres,
            @NotNull(message = "Los apellidos son obligatorios") String apellidos,
            @NotNull(message = "El username es obligatorio") String nombreUsuario,
            @NotNull(message = "El correo es obligatorio") @Email String correo,
            @NotNull(message = "La contraseña es obligatoria") @Size(min = 8) String contraseña) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.contraseña = contraseña;
    }

    public DtoUsuarioIn() {
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    
}