package com.product.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.product.api.entity.Usuario;
import com.product.api.repository.RepoUsuario;

@Service
public class SvcUserAuth implements UserDetailsService{

    @Autowired
    private RepoUsuario repo;

    @Override
	public Usuario loadUserByUsername(String principal) throws UsernameNotFoundException {
		
		Optional<Usuario> usuarioOptional;
		Usuario usuario;

		usuarioOptional = repo.findByUsername(principal);

		if(usuarioOptional.isPresent()) {
			usuario = usuarioOptional.get();
			return usuario;
		}

		usuario = repo.findByCorreo(principal).orElseThrow(() -> new UsernameNotFoundException("Usuario inexistente"));
		return usuario;
	}
}