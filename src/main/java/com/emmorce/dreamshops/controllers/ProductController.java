package com.emmorce.dreamshops.controllers;

import com.emmorce.dreamshops.exceptions.ResourceNotFoundException;
import com.emmorce.dreamshops.model.Product;
import com.emmorce.dreamshops.request.AddProductRequest;
import com.emmorce.dreamshops.request.ProductUpdateRequest;
import com.emmorce.dreamshops.response.ApiResponse;
import com.emmorce.dreamshops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProduct() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity
                .ok(new ApiResponse("Products fetched successfully", products));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
        try {
            Product product = productService.getProductById(productId);
            return ResponseEntity
                    .ok(new ApiResponse("Product fetched successfully", product));
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity
                    .status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<ApiResponse> addProduct(@PathVariable Long productId, @RequestBody AddProductRequest product) {
        try {
            Product addedProduct = productService.addProduct(product);
            return ResponseEntity
                   .ok(new ApiResponse("Product added successfully", addedProduct));
        } catch (Exception e) {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long productId, @RequestBody ProductUpdateRequest product) {
        try {
            Product updatedProduct = productService.updateProduct(product, productId);
            return ResponseEntity
                   .ok(new ApiResponse("Product updated successfully", updatedProduct));
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity
                   .status(NOT_FOUND)
                   .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity
                   .status(INTERNAL_SERVER_ERROR)
                   .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProductById(productId);
            return ResponseEntity
                   .ok(new ApiResponse("Product deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity
                   .status(NOT_FOUND)
                   .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity
                   .status(INTERNAL_SERVER_ERROR)
                   .body(new ApiResponse(e.getMessage(), null));
        }
    }

    // get product by brand and name
    @RequestMapping("/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try {
            List<Product> products = productService.getAllProductsByBrandAndName(brand, name);
            if(products.isEmpty()){
                return ResponseEntity
                       .status(NOT_FOUND)
                       .body(new ApiResponse("No products found" , name));
            }
            return ResponseEntity
                   .ok(new ApiResponse("Products fetched successfully", products));
        } catch (Exception e) {
            return  ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

//    get products by category and brand
    @RequestMapping("/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(@RequestParam String category, @RequestParam String brand) {
        try {
            List<Product> products = productService.getAllProductsByCategoryAndBrand(category, brand);
            if(products.isEmpty()){
                return ResponseEntity
                       .status(NOT_FOUND)
                       .body(new ApiResponse("No products found" , category));
            }
            return ResponseEntity
                   .ok(new ApiResponse("Products fetched successfully", products));
        } catch (Exception e) {
            return  ResponseEntity
                   .status(INTERNAL_SERVER_ERROR)
                   .body(new ApiResponse(e.getMessage(), null));
        }
    }

    // get product by name
    @RequestMapping("/by/name/{name}")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name) {
        try {
            List<Product> products = productService.getAllProductsByName(name);
            if(products.isEmpty()){
                return ResponseEntity
                       .status(NOT_FOUND)
                       .body(new ApiResponse("No products found" , name));
            }
            return ResponseEntity
                   .ok(new ApiResponse("Products fetched successfully", products));
        } catch (Exception e) {
            return  ResponseEntity
                   .status(INTERNAL_SERVER_ERROR)
                   .body(new ApiResponse(e.getMessage(), null));
        }
    }

//    find product by brand
    @RequestMapping("/by/brand/{brand}")
    public ResponseEntity<ApiResponse> getProductsByBrand(@PathVariable String brand) {
        try {
            List<Product> products = productService.getAllProductsByBrand(brand);
            if(products.isEmpty()){
                return ResponseEntity
                       .status(NOT_FOUND)
                       .body(new ApiResponse("No products found" , brand));
            }
            return ResponseEntity
                   .ok(new ApiResponse("Products fetched successfully", products));
        } catch (Exception e) {
            return  ResponseEntity
                   .status(INTERNAL_SERVER_ERROR)
                   .body(new ApiResponse(e.getMessage(), null));
        }

    }

//    get product by category
    @RequestMapping("/by/category/{category}")
    public ResponseEntity<ApiResponse> getProductsByCategory(@PathVariable String category) {
        try {
            List<Product> products = productService.getAllProductsByCategory(category);
            if(products.isEmpty()){
                return ResponseEntity
                       .status(NOT_FOUND)
                       .body(new ApiResponse("No products found" , category));
            }
            return ResponseEntity
                   .ok(new ApiResponse("Products fetched successfully", products));
        } catch (Exception e) {
            return  ResponseEntity
                   .status(INTERNAL_SERVER_ERROR)
                   .body(new ApiResponse(e.getMessage(), null));
        }
    }

//    count products by brand and name
    @GetMapping("/count/by/brand-and-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try {
            Long count = productService.countProductsByBrandAndName(brand, name);
            return ResponseEntity
                   .ok(new ApiResponse("Product count fetched successfully", count));
        } catch (Exception e) {
            return  ResponseEntity
                   .status(INTERNAL_SERVER_ERROR)
                   .body(new ApiResponse(e.getMessage(), null));
        }
    }

}
