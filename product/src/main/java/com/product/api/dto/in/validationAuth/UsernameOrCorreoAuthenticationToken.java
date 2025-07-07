package com.product.api.dto.in.validationAuth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class UsernameOrCorreoAuthenticationToken extends AbstractAuthenticationToken {

    // Versión de serialización para la clase
    private static final long serialVersionUID = 8008995706968351846L;

    // Guarda el identificador del usuario: nombre de usuario o correo
    private final String principal;
    // Contiene la contraseña en texto plano para validación
    private final String credentials;

    /**
     * Crea un token no autenticado a partir de un mapa que contenga
     * la clave "username" o "correo" y la contraseña.
     *
     * @param datos       Mapa con los datos del usuario ("username" o "correo")
     * @param credentials Contraseña sin encriptar
     */
    public UsernameOrCorreoAuthenticationToken(Map<String, String> datos, String credentials) {
        // Llama al constructor padre sin autoridades asignadas
        super(null);
        // Si existe "username" lo usa, si no, toma "correo"
        this.principal = datos.containsKey("username")
                ? datos.get("username")
                : datos.getOrDefault("correo", null);
        this.credentials = credentials;
        // Marca el token como no autenticado
        setAuthenticated(false);
    }

    /**
     * Retorna las credenciales (la contraseña) para la autenticación.
     */
    @Override
    public Object getCredentials() {
        return credentials;
    }

    /**
     * Retorna el principal, es decir, el nombre de usuario o el correo.
     */
    @Override
    public Object getPrincipal() {
        return principal;
    }
}