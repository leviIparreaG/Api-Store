package com.product.api.dto.in;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

public class DtoProductImageIn {

    
    @JsonProperty("product_id")
    @NotNull(message = "El id del producto es obligatorio")
    private Integer productId;


    @JsonProperty("image")
    @NotNull(message = "La image del producto es obligatoria")
    private String image;

    
    public Integer getProductId() {
        return productId;
    }

    
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    
    public String getImage() {
        return image;
    }

    
    public void setImage(String image) {
        this.image = image;
    }
}
