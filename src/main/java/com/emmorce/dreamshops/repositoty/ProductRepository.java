package com.emmorce.dreamshops.repositoty;

import com.emmorce.dreamshops.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategoryName(String category);

    List<Product> findAllByBrand(String brand);

    List<Product> findAllByCategoryNameAndBrand(String category, String brand);

    List<Product> findAllByName(String name);

    List<Product> findByBrandAndName(String brand, String name);

    long countProductsByBrandAndName(String brand, String name);
}
