package com.product.api.service;
import java.util.List;


import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.commons.dto.ApiResponse;

import org.springframework.http.ResponseEntity;


public interface SvcCategory {
	public ResponseEntity<List<Category>>  getCategories();
	
	public ResponseEntity<List<Category>> getActiveCategories();
	public ResponseEntity<ApiResponse> createCategory(DtoCategoryIn in);
	public ResponseEntity<ApiResponse> updateCategory(DtoCategoryIn in, Integer id);
	public ResponseEntity<ApiResponse> enableCategory(Integer id);
	public ResponseEntity<ApiResponse> disableCategory(Integer id);
	public ResponseEntity<Category> getCategory(Integer id);
}
