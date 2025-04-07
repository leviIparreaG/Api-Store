package com.product.api.service;

import org.springframework.http.ResponseEntity;

import com.product.api.dto.in.DtoCustomerImageIn;
import com.product.api.commons.dto.ApiResponse;

public interface SvcCustomerImage {
	
	public ResponseEntity<ApiResponse> uploadCustomerImage(DtoCustomerImageIn in);

}
