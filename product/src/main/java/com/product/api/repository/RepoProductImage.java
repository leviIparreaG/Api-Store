package com.product.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.product.api.entity.ProductImage;

@Repository
public interface RepoProductImage extends JpaRepository<ProductImage, Integer>{

    
    @Query(value = "SELECT * FROM product_image WHERE product_id = :product_id;", nativeQuery = true)
    ProductImage findByProductId(Integer product_id); 

    
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM product_image WHERE product_image_id = :product_image_id;", nativeQuery = true)
    void deleteProductImage(Integer product_image_id);

    
    @Query(value = "SELECT * FROM product_image WHERE product_image_id = :product_image_id;", nativeQuery = true)
    ProductImage findByProductImageId(Integer product_image_id);

    
    @Query(value = "SELECT * FROM product_image WHERE product_id = :product_id;", nativeQuery = true)
    List<ProductImage> findAllImagesByProductId(Integer product_id);
}
