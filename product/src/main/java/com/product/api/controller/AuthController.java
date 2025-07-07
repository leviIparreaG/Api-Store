package com.product.api.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.commons.Globales;
import com.product.api.commons.dto.AuthApiResponse;
import com.product.api.dto.in.AuthRequest;
import com.product.api.dto.in.validationAuth.UsernameOrCorreoAuthenticationToken;
import com.product.api.entity.Usuario;
import com.product.config.jwt.JwtUtil;

import jakarta.validation.Valid;

@RestController
public class AuthController {

    @Autowired
    // Gestiona el proceso de autenticación con Spring Security
    private AuthenticationManager authenticationManager;
    
    @Autowired
    // Utilidad para generar y validar tokens JWT
    private JwtUtil jwtUtil;

    // Endpoint para iniciar sesión y obtener un JWT
    @PostMapping(value = "/login")
    public ResponseEntity<AuthApiResponse> autenticaUsuario(
            @Valid @RequestBody AuthRequest request) {

        // Objeto de respuesta que contendrá el token y detalles
        AuthApiResponse response = new AuthApiResponse();

        // Map para pasar credenciales (username o correo) al AuthenticationManager
        HashMap<String, String> elementosAutenticacion = new HashMap<>();

        // Si se proporcionó nombre de usuario, lo añadimos
        if (StringUtils.hasLength(request.getNombreUsuario()))
            elementosAutenticacion.put("username", request.getNombreUsuario());
        // Si se proporcionó correo, lo añadimos
        if (StringUtils.hasLength(request.getCorreo()))
            elementosAutenticacion.put("correo", request.getCorreo());

        // Realiza la autenticación usando un token personalizado
        Authentication authenticate = authenticationManager.authenticate(
            new UsernameOrCorreoAuthenticationToken(
                elementosAutenticacion,       // credenciales
                request.getContrasena()       // contraseña
            )
        );

        // Agrega mensaje de éxito en la respuesta
        response.setDetalles(Arrays.asList("Autenticación exitosa"));

        // Genera el token JWT a partir del usuario autenticado
        String jwt = jwtUtil.generateToken((Usuario) authenticate.getPrincipal());

        // Asigna el token y la fecha/hora de emisión a la respuesta
        response.setToken(jwt);
        response.setFechaHora(Globales.formatDate(new Date()));

        // Devuelve la respuesta con código 200 OK
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}