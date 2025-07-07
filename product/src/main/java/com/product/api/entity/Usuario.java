package com.product.api.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
@JsonInclude(Include.NON_EMPTY)
public class Usuario implements Serializable, UserDetails{

    private static final long serialVersionUID = 3859609465552592790L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("usuario_id")
    @Column(name = "usuario_id")
    private Long id;

    //nombres, apellidos, username, correo, password
    @JsonProperty("nombres")
    @Column(name = "nombres")
    private String nombres;

    @JsonProperty("apellidos")
    @Column(name = "apellidos")
	private String apellidos;

    @JsonProperty("username")
    @Column(name = "username")
    private String username;

    @JsonProperty("correo")
    @Column(name = "correo")
    private String correo;
	
    @JsonProperty("password")
    @Column(name = "password")
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();

    @JsonProperty("activo")
    @Column(name = "activo")
    private Boolean activo;

    public Usuario(String nombreUsuario, String correo) {
    	this.username = nombreUsuario;
    	this.correo = correo;
    }

    public Usuario() {
    }



    @Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
	}

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    
}