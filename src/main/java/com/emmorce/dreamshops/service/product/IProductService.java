package com.emmorce.dreamshops.service.product;

import com.emmorce.dreamshops.model.Product;
import com.emmorce.dreamshops.request.AddProductRequest;
import com.emmorce.dreamshops.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    Product getProductById(long id);
    Product updateProduct(ProductUpdateRequest product, long prodcutId);
    void deleteProductById(long id);

    List<Product> getAllProducts();
    List<Product> getAllProductsByCategory(String category);
    List<Product> getAllProductsByBrand(String brand);
    List<Product> getAllProductsByCategoryAndBrand(String category, String brand);
    List<Product> getAllProductsByName(String name);
    List<Product> getAllProductsByBrandAndName(String brand, String name);

    long countProductsByBrandAndName(String brand, String name);


}
