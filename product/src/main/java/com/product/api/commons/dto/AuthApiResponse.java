package com.product.api.commons.dto;

import java.util.ArrayList;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.api.dto.out.DtoUsuarioOut;
import com.product.api.dto.out.InfoPaginacion;

import java.util.logging.Logger;

//Incluir solo campos no vacíos en la serialización JSON
@JsonInclude(Include.NON_EMPTY)
public class AuthApiResponse {

 // Logger para registrar errores de serialización
 private static final Logger log = Logger.getLogger(AuthApiResponse.class.getName());

 // Token JWT generado al autenticar
 private String token;
 // Fecha y hora de emisión del token
 private String fechaHora;
 // Lista de mensajes de detalle o errores
 private List<String> detalles;
 // Lista de usuarios cuando se retorna más de uno
 private List<DtoUsuarioOut> usuarios;
 // Información de paginación para listados de usuarios
 private InfoPaginacion infoPaginacion;

 // Constructor sin argumentos (requerido por frameworks)
 public AuthApiResponse() {
 }

 // Constructor completo para inicializar todos los campos
 public AuthApiResponse(String token, String fechaHora, List<String> detalles,
                        List<DtoUsuarioOut> usuarios, InfoPaginacion infoPaginacion) {
     this.token = token;
     this.fechaHora = fechaHora;
     this.detalles = detalles;
     this.usuarios = usuarios;
     this.infoPaginacion = infoPaginacion;
 }

 // Devuelve el token JWT
 public String getToken() {
     return token;
 }

 // Asigna el token JWT
 public void setToken(String token) {
     this.token = token;
 }

 // Devuelve la fecha y hora de emisión
 public String getFechaHora() {
     return fechaHora;
 }

 // Asigna la fecha y hora de emisión
 public void setFechaHora(String fechaHora) {
     this.fechaHora = fechaHora;
 }

 // Devuelve la lista de detalles o mensajes
 public List<String> getDetalles() {
     return detalles;
 }

 // Asigna la lista de detalles o mensajes
 public void setDetalles(List<String> detalles) {
     this.detalles = detalles;
 }

 // Devuelve la lista de usuarios
 public List<DtoUsuarioOut> getUsuarios() {
     return usuarios;
 }

 // Asigna la lista de usuarios
 public void setUsuarios(List<DtoUsuarioOut> usuarios) {
     this.usuarios = usuarios;
 }

 // Devuelve la información de paginación
 public InfoPaginacion getInfoPaginacion() {
     return infoPaginacion;
 }

 // Asigna la información de paginación
 public void setInfoPaginacion(InfoPaginacion infoPaginacion) {
     this.infoPaginacion = infoPaginacion;
 }

 // Serializa el objeto a JSON para toString
 @Override
 public String toString() {
     try {
         ObjectMapper objectMapper = new ObjectMapper();
         return objectMapper.writeValueAsString(this);
     } catch (JsonProcessingException e) {
         // Loguea el error y devuelve toString por defecto
         log.severe("Error al serializar AuthApiResponse: " + e.getMessage());
         return super.toString();
     }
 }

 // Agrega un usuario a la lista, inicializándola si está nula
 public void agregaUsuario(DtoUsuarioOut usuario) {
     if (this.usuarios == null) {
         this.usuarios = new ArrayList<>();
     }
     this.usuarios.add(usuario);
 }
}

