package com.product.config.security;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.product.api.dto.in.validationAuth.UsernameOrCorreoAuthenticationProvider;
import com.product.config.jwt.JwtAuthFilter;



@Configuration
public class SecurityConfig {
	
    @Autowired
    // Filtro que valida el JWT en cada petición
	private JwtAuthFilter jwtFilter;
	
    /**
     * Define la cadena de filtros de seguridad para la aplicación.
     * - Deshabilita CSRF (por ser API stateless).
     * - Configura permisos por ruta y método HTTP.
     * - Habilita CORS según CorsConfig.
     * - Deshabilita formulario de login y usa HTTP Basic.
     * - Establece política de sesión stateless.
     * - Agrega el filtro de JWT antes de la autenticación por defecto.
     */
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfig corsConfig) throws Exception {
	
		http
            // Deshabilita la protección CSRF (no hay sesión de usuario)
			.csrf(AbstractHttpConfigurer::disable)
            // Configura autorización de peticiones HTTP
			.authorizeHttpRequests(auth -> auth

                // Rutas públicas (errores, Swagger UI, OpenAPI y estado del sistema)
				.requestMatchers(
                    "/error", 
                    "/swagger-ui/**", 
                    "/v3/api-docs/**", 
                    "/actuator/info", 
                    "/actuator/health"
                ).permitAll()

                // Endpoint de login accesible para todos
				.requestMatchers(HttpMethod.POST, "/login").permitAll()

                // Endpoints de usuario:
                // Crear usuario público, listar usuarios solo ADMIN
				.requestMatchers(HttpMethod.POST, "/usuario").permitAll()
				.requestMatchers(HttpMethod.GET, "/usuario").hasAuthority("ADMIN")

                // Endpoints de categoría:
                // Consulta de categorías activas pública, resto solo ADMIN
				.requestMatchers(HttpMethod.GET, "/category/active").permitAll()
				.requestMatchers("/category/**").hasAuthority("ADMIN")

                // Endpoints de producto:
                // Consulta de detalle pública, CRUD completo solo ADMIN
				.requestMatchers(HttpMethod.GET, "/product/{id}").permitAll()
				.requestMatchers("/product/**").hasAuthority("ADMIN")

                // Endpoints de imágenes de producto solo ADMIN
				.requestMatchers("/product-image/**").hasAuthority("ADMIN")
			)
            // Configura CORS usando la fuente definida en CorsConfig
			.cors(cors -> cors.configurationSource(corsConfig))
            // Habilita HTTP Basic Authentication (sin mostrar formulario)
			.httpBasic(Customizer.withDefaults())
            // Deshabilita el login por formularios de Spring Security
			.formLogin(form -> form.disable())
            // Configura la gestión de sesiones como stateless
			.sessionManagement(sess -> sess
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            // Agrega el filtro JWT antes del filtro de username/password
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
			
		// Construye la configuración de seguridad final
		return http.build();
	}

    /**
     * Configura el AuthenticationManager usando un proveedor personalizado
     * que permite autenticación por nombre de usuario o correo.
     */
	@Bean
    AuthenticationManager authenticationManager(UsernameOrCorreoAuthenticationProvider provider) {
        return new ProviderManager(List.of(provider));
    }
	
    // Codificador de contraseñas BCrypt para el registro y comparación
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}	
	
    /**
     * Método de utilidad para generar el hash de una contraseña
     * (solo para pruebas; no se usa en producción así).
     */
	public static void main(String[] args) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		System.out.print(bCryptPasswordEncoder.encode("contrasenaSegura"));
	}
}


