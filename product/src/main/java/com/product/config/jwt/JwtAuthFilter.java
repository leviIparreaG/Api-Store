package com.product.config.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    // Utilitario para extraer datos del JWT
    private final JwtUtil jwtUtil;

    // Inyección de la dependencia JwtUtil
    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * Intercepta cada petición HTTP para validar el token JWT.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {
        
        // Obtiene el encabezado Authorization
        String authHeader = request.getHeader("Authorization");
        
        // Si no existe o no comienza con "Bearer ", continúa sin autenticar
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // Extrae el token (remueve el prefijo "Bearer ")
        String token = authHeader.substring(7);
        // Obtiene el nombre de usuario desde el token
        String username = jwtUtil.extractUsername(token);
        // Extrae la lista de permisos (claims) del token
        List<HashMap<String, String>> permisos = jwtUtil.extractPermisos(token);
        
        // Convierte la lista de mapas a una lista de cadenas de autoridad
        List<String> permisosList = permisos.stream()
                                            .map(i -> i.get("authority"))
                                            .toList();
        
        // Si hay un usuario válido y no está autenticado en el contexto
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Construye un UserDetails con el nombre de usuario y sus permisos
            UserDetails userDetails = User.withUsername(username)
                                          .password("")  // contraseña no necesaria aquí
                                          .authorities(permisosList.toArray(new String[0]))
                                          .build();

            // Crea el token de autenticación para Spring Security
            UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            
            // Registra la autenticación en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // Continúa con la cadena de filtros
        chain.doFilter(request, response);
    }
}