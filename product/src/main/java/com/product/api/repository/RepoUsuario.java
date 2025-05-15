package com.product.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.product.api.entity.Usuario;

@Repository
public interface RepoUsuario extends JpaRepository<Usuario, Long>{

    @Query(value = "SELECT * FROM usuario WHERE username = :username", nativeQuery = true)
    Optional<Usuario> findByUsername(@Param("username") String username);

    @Query(value = "SELECT * FROM usuario WHERE username = :username AND password = :password", nativeQuery = true)
    Optional<Usuario> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Query(value = "SELECT * FROM usuario WHERE correo = :correo", nativeQuery = true)
    Optional<Usuario> findByCorreo(@Param("correo") String correo);

    @Query(value = "SELECT * FROM usuario WHERE correo = :correo AND password = :password", nativeQuery = true)
    Optional<Usuario> findByCorreoAndPassword(@Param("correo") String correo, @Param("password") String password);

    
}