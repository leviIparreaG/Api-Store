package com.product.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.product.api.commons.Globales;
import com.product.api.commons.Rol;
import com.product.api.commons.dto.AuthApiResponse;
import com.product.api.dto.in.DtoUsuarioIn;
import com.product.api.dto.out.DtoUsuarioOut;
import com.product.api.dto.out.InfoPaginacion;
import com.product.api.entity.Usuario;
import com.product.api.repository.RepoUsuario;

import jakarta.validation.Valid;

@Service
public class SvcUsuarioImp implements SvcUsuario{

    @Autowired
    private RepoUsuario repoUsuario;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public AuthApiResponse registraUsuario(@Valid DtoUsuarioIn in) {

        Boolean exitoPrecondiciones = verificaPrecondicionesRegistro(in);

        if(!exitoPrecondiciones.booleanValue()) {
            AuthApiResponse usuarioExistenteResponse = new AuthApiResponse();
            usuarioExistenteResponse.setDetalles(Arrays.asList("Registro de usuario fallido. Usuario ya existente"));
            usuarioExistenteResponse.setFechaHora(Globales.formatDate(new Date()));
            usuarioExistenteResponse.setToken(null);
            return usuarioExistenteResponse;
        }

        Usuario usuarioNuevo = usuarioRequestToUsuario(in);
        HashSet<String> roles = new HashSet<String>();
        roles.add(Rol.ROLE_USER.getNombreRol());
        usuarioNuevo.setRoles(roles);
        Usuario usuario = repoUsuario.save(usuarioNuevo); //checar esto

        AuthApiResponse usuarioCreadoResponse = new AuthApiResponse();
        usuarioCreadoResponse.setDetalles(Arrays.asList("Usuario creado exitosamente"));
        usuarioCreadoResponse.setFechaHora(Globales.formatDate(new Date()));
        usuarioCreadoResponse.agregaUsuario(usuarioToUsuarioResponse(usuario));

        return usuarioCreadoResponse;
                
    }

    @Override
    public AuthApiResponse consultaUsuarios(Integer pagina, Integer tam) {
        
        AuthApiResponse response = new AuthApiResponse();
        Pageable pageable = PageRequest.of(pagina, tam);
        Page<Usuario> paginaUsuarios = repoUsuario.findAll(pageable);
        List<Usuario> usuarios = paginaUsuarios.getContent();

        response.setUsuarios(usuarios.stream().map(this::usuarioToUsuarioResponse).toList());
		response.setInfoPaginacion(new InfoPaginacion(pagina, paginaUsuarios.hasNext(), paginaUsuarios.hasPrevious(), paginaUsuarios.getTotalPages(), paginaUsuarios.getSize(), paginaUsuarios.getTotalElements()));
		
        return response;
    }
        

    private Boolean verificaPrecondicionesRegistro(@Valid DtoUsuarioIn in) {
        Optional<Usuario> byCorreo = repoUsuario.findByCorreo(in.getCorreo());
        Optional<Usuario> byUsername = repoUsuario.findByUsername(in.getNombreUsuario());

        if(byCorreo.isPresent() || byUsername.isPresent()) {
            return false;
        }
        return true;
    }

    private Usuario usuarioRequestToUsuario(DtoUsuarioIn in) {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombres(in.getNombres());
        nuevoUsuario.setApellidos(in.getApellidos());
        nuevoUsuario.setPassword(passwordEncoder.encode(in.getContraseña()));
        nuevoUsuario.setCorreo(in.getCorreo());
        nuevoUsuario.setActivo(true);
        nuevoUsuario.setUsername(in.getNombreUsuario());
        nuevoUsuario.setRoles(null);
        return nuevoUsuario;
    }

    private DtoUsuarioOut usuarioToUsuarioResponse(Usuario usuario) {
        DtoUsuarioOut usuarioOut = new DtoUsuarioOut();
        usuarioOut.setNombres(usuario.getNombres());
        usuarioOut.setApellidos(usuario.getApellidos());
        usuarioOut.setCorreo(usuario.getCorreo());
        usuarioOut.setEsActivo(usuario.getActivo());
        usuarioOut.setNombreUsuario(usuario.getUsername());
        usuarioOut.setRoles(usuario.getRoles());
        return usuarioOut;
    }
}