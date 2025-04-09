package com.product.api.controller;

import java.util.List;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.service.SvcCategory;
import com.product.api.commons.dto.ApiResponse;
import com.product.exception.ApiException;
import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController 
@RequestMapping("/category") 
@Tag(name = "Categoría", description = "Catálogo de categorias")
public class CtrlCategory {
	
	@Autowired
	SvcCategory svc;
	

    @GetMapping 
    public ResponseEntity<List<Category>> getCategories() {
    	
    	return svc.getCategories();
    }
    
    
    
    @GetMapping("/active")
    public ResponseEntity<List<Category>> getActiveCategories(){
    	return svc.getActiveCategories();
    }
    
    @PostMapping
	public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody DtoCategoryIn in, BindingResult bindingResult){
    	if(bindingResult.hasErrors())
    		throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
    	return svc.createCategory(in);
	}
    
    @PutMapping("/{id}")
	public ResponseEntity<ApiResponse> updateCategory(@Valid @RequestBody DtoCategoryIn in, @PathVariable("id") Integer id, BindingResult bindingResult){
    	if (bindingResult.hasErrors())
			throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getFieldError().getDefaultMessage());
		return svc.updateCategory(in, id);
	}
	
    @PatchMapping("/{id}/enable")
	public ResponseEntity<ApiResponse> enableCategory(@PathVariable("id") Integer id){
    	return svc.enableCategory(id);
    }
    
    @PatchMapping("/{id}/disable")
	public ResponseEntity<ApiResponse> disableCategory(@PathVariable("id") Integer id){
    	return svc.disableCategory(id);
    }
    
    @GetMapping("/{id}")
	public ResponseEntity<Category> getCategory(@PathVariable Integer id) {
		return svc.getCategory(id);
	}
    
    
}