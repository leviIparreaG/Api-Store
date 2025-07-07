package com.product.api.dto.in.validationAuth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsernameOrCorreoAuthenticationProvider implements AuthenticationProvider {

    // Servicio para cargar los detalles de usuario por nombre o correo
    private final UserDetailsService userDetailsService;
    // Codificador de contraseñas para comparar hashes
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsernameOrCorreoAuthenticationProvider(UserDetailsService userDetailsService,
                                                  PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Realiza la autenticación usando un token que acepta nombre de usuario o correo.
     * @param authentication contiene las credenciales (principal y contraseña)
     * @return objeto Authentication con el usuario y sus roles si es válido
     * @throws AuthenticationException si las credenciales no son válidas
     */
    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        // principal puede ser nombre de usuario o correo
        final String principal = (String) authentication.getPrincipal();
        // contraseña en texto plano
        final String rawPassword = (String) authentication.getCredentials();

        // Intentamos cargar el usuario; si no existe, lanzamos BadCredentialsException
        final UserDetails user = Optional.of(principal)
            .map(nameOrEmail -> {
                try {
                    return userDetailsService.loadUserByUsername(nameOrEmail);
                } catch (UsernameNotFoundException ex) {
                    throw new BadCredentialsException("Credenciales inválidas", ex);
                }
            })
            .orElseThrow(() -> new BadCredentialsException("Credenciales inválidas"));

        // Verificamos que la contraseña proporcionada coincida con la almacenada
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new BadCredentialsException("Credenciales inválidas");
        }

        // Retornamos un token de autenticación con el usuario y sus autoridades
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    /**
     * Indica que este provider maneja tokens de tipo UsernameOrCorreoAuthenticationToken
     */
    @Override
    public boolean supports(final Class<?> authentication) {
        return UsernameOrCorreoAuthenticationToken.class.isAssignableFrom(authentication);
    }
}