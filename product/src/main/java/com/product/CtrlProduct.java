package com.product;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController 
@RequestMapping("/category") 
public class CtrlProduct {
	

    @GetMapping 
    public List<Category> getCategories() {
    	
    	List<Category> ListaCategorias = new ArrayList<Category>();
    	
    	ListaCategorias.add(new Category(1, "Lentes", "lts"));
    	ListaCategorias.add(new Category(1, "Relojes", "rljs"));
    	
    	return ListaCategorias;
    }
}
