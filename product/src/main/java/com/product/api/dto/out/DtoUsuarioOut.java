package com.product.api.dto.out;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonInclude(Include.NON_NULL)
public class DtoUsuarioOut {

    private static final Logger log = LoggerFactory.getLogger(DtoUsuarioOut.class);

    private String nombres;
    private String apellidos;
    private String nombreUsuario;
    private String correo;
    private Set<String> roles;
    private Boolean esActivo;

    
    public DtoUsuarioOut() {}
   
    public DtoUsuarioOut(String nombres, String apellidos, String nombreUsuario, String correo, Set<String> roles, Boolean esActivo) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.roles = roles;
        this.esActivo = esActivo;
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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Boolean getEsActivo() {
        return esActivo;
    }

    public void setEsActivo(Boolean esActivo) {
        this.esActivo = esActivo;
    }

    // toString usando ObjectMapper
    @Override
    public String toString() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error("Excepción atrapada al serializar objeto: " + e.getMessage());
            return super.toString();
        }
    }
}