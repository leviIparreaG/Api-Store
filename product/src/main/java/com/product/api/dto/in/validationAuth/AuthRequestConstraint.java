package com.product.api.dto.in.validationAuth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

//Define una validación personalizada para la clase AuthRequest
@Constraint(validatedBy = AuthRequestValidator.class)
//Aplica esta anotación a nivel de clase (tipo)
@Target({ElementType.TYPE})
//Conserva la anotación en tiempo de ejecución
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthRequestConstraint {

 // Mensaje que se muestra si la validación falla
 String message() default "La solicitud no cumple con la especificación requerida";

 // Grupos de validación (opcional, no usado normalmente)
 Class<?>[] groups() default {};

 // Payload para información adicional de la validación
 Class<? extends Payload>[] payload() default {};
}