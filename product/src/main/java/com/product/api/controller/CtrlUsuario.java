package com.product.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.commons.dto.AuthApiResponse;
import com.product.api.dto.in.DtoUsuarioIn;
import com.product.api.service.SvcUsuario;
import com.product.exception.ApiException;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/usuario")
public class CtrlUsuario {

    @Autowired
    SvcUsuario svc;

    @PostMapping
    public ResponseEntity<AuthApiResponse> registraUsuario(@Valid @RequestBody DtoUsuarioIn in, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
			throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getFieldError().getDefaultMessage());

        
        AuthApiResponse response = svc.registraUsuario(in);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<AuthApiResponse> consultaUsuarios(@RequestParam(defaultValue = "0") Integer pagina, @RequestParam(defaultValue = "10") Integer tam) {

        StringBuilder sb = new StringBuilder();
		sb.append("Parametros: ");
		sb.append("Página: ");
		sb.append(pagina);
		sb.append("Tamaño Página: ");
		sb.append(tam);

        AuthApiResponse response = svc.consultaUsuarios(pagina, tam);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}