package com.product.api.service;

import com.product.api.commons.dto.AuthApiResponse;
import com.product.api.dto.in.DtoUsuarioIn;

import jakarta.validation.Valid;

public interface SvcUsuario {

    public AuthApiResponse registraUsuario(@Valid DtoUsuarioIn in);
    public AuthApiResponse consultaUsuarios(Integer pagina, Integer tam);
}