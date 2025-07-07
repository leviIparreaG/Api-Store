package com.product.api.dto.in.validationAuth;

import org.springframework.util.StringUtils;

import com.product.api.dto.in.AuthRequest;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

//Validador personalizado para AuthRequest
public class AuthRequestValidator implements ConstraintValidator<AuthRequestConstraint, AuthRequest> {

 // Lógica de validación: requiere correo o nombre de usuario
 @Override
 public boolean isValid(AuthRequest request, ConstraintValidatorContext context) {
     // Devuelve true si al menos uno de los dos campos no está vacío
     return StringUtils.hasLength(request.getCorreo()) 
         || StringUtils.hasLength(request.getNombreUsuario());
 }
}