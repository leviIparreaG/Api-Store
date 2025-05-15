package com.product.config.openapi;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;


@Configuration
public class OpenApiConfig {

    // Define un bean OpenAPI con la información básica de la API
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                // Título que aparecerá en la interfaz de Swagger
                .title("DWB2025-2 - API Customer")
                .version("0.0.1")
                .description("API para la gestión clientes para la tienda en línea FCiencias Store."));
    }

    // Define un bean para ordenar alfabéticamente los esquemas (models) en Swagger UI
    @Bean
    public OpenApiCustomizer sortSchemasAlphabetically() {
        return openApi -> {
            // Obtiene los componentes de la especificación OpenAPI
            Components components = openApi.getComponents();
            // Verifica que existan esquemas para ordenar
            if (components != null && components.getSchemas() != null) {
                // Ordena los esquemas por clave (nombre) y los recopila en un LinkedHashMap
                Map<String, Schema> sortedSchemas = components.getSchemas().entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue,
                            LinkedHashMap::new
                        ));
                // Reemplaza los esquemas originales por los ordenados
                components.setSchemas(sortedSchemas);
            }
        };
    }

}