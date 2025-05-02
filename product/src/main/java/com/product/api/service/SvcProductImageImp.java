package com.product.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.api.commons.dto.ApiResponse;
import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.entity.ProductImage;
import com.product.api.repository.RepoProductImage;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;

@Service
public class SvcProductImageImp implements SvcProductImage{

    @Autowired
    RepoProductImage repo;

    @Value("${app.upload.dir}")
    private String uploadDir;

    
    @Override
    public ResponseEntity<ApiResponse> createProductImage(DtoProductImageIn in) {
        try {

            if (in.getImage().startsWith("data:image")) {
                int commaIndex = in.getImage().indexOf(",");
                    if (commaIndex != -1) {
                        in.setImage(in.getImage().substring(commaIndex + 1));
                    }
            }
		    
            byte[] imageBytes = Base64.getDecoder().decode(in.getImage());
            String fileName = UUID.randomUUID().toString() + ".png";
            Path imagePath = Paths.get(uploadDir, "img", "product", fileName);
            Files.createDirectories(imagePath.getParent());
            Files.write(imagePath, imageBytes);

            ProductImage productImage = new ProductImage();
            productImage.setProductId(in.getProductId());
            productImage.setImage("/img/product/" + fileName);
            productImage.setStatus(1);

            repo.save(productImage);

		    return new ResponseEntity<>(new ApiResponse("La imagen ha sido registrada"), HttpStatus.OK);
		}catch (DataAccessException e) {
		    throw new DBAccessException(e);
		}catch (IOException ioe) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el archivo");
        }
    }

    
    @Override
    public ResponseEntity<ApiResponse> deleteProductImage(Integer product_image_id) {
        try {
            ProductImage productImage = repo.findByProductImageId(product_image_id);

            if(productImage == null)
                throw new ApiException(HttpStatus.NOT_FOUND, "El id de la imagen no existe");

            String relativePath = productImage.getImage();
            if (relativePath.startsWith("/")) {
                relativePath = relativePath.substring(1);
            }
            Path path = Paths.get(uploadDir, relativePath);
            Files.deleteIfExists(path);
            
            repo.deleteProductImage(product_image_id);
            return new ResponseEntity<>(new ApiResponse("La imagen ha sido eliminada"), HttpStatus.OK);
        }catch (DataAccessException e){
            throw new DBAccessException(e);
        }catch (IOException ioe) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar el archivo");
        }
    }
}
