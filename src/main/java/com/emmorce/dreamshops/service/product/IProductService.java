package com.emmorce.dreamshops.service.product;

import com.emmorce.dreamshops.model.Product;

import java.util.List;

public interface IProductService {
    Product addProduct(Product product);
    Product getProductById(long id);
    void deleteProductById(long id);
    void updateProduct(Product product, long prodcutId);

    List<Product> getAllProducts();
    List<Product> getAllProductsByCategory(String category);
    List<Product> getAllProductsByBrand(String brand);
    List<Product> getAllProductsByCategoryAndBrand(String category, String brand);
    List<Product> getAllProductsByName(String name);
    List<Product> getAllProductsByBrandAndName(String brand, String name);

    long countProductsByBrandAndName(String brand, String name);


}
