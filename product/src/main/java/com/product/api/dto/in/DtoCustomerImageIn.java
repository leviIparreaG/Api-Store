package com.product.api.dto.in;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

public class DtoCustomerImageIn {

    @JsonProperty("product_id")
    @NotNull(message = "El id del producto es obligatorio")
    private Integer product_id;

    @JsonProperty("image")
    @NotNull(message = "El image es obligatorio")
    private String image;


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }
}
