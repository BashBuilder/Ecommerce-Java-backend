package com.emmorce.dreamshops.service.product;

import com.emmorce.dreamshops.exceptions.ProductNotFoundException;
import com.emmorce.dreamshops.model.Category;
import com.emmorce.dreamshops.model.Product;
import com.emmorce.dreamshops.repositoty.CategoryRepository;
import com.emmorce.dreamshops.repositoty.ProductRepository;
import com.emmorce.dreamshops.request.AddProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProductRequest request) {
        // check if the category is found in the database
        // if yes set it as the new product category
        // but if no then save it as a new category
        // then set as the new product category
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
            .orElseGet(() -> {
                Category newCategory = new Category(request.getCategory().getName());
                return categoryRepository.save(newCategory);
            });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));

    }

    private Product createProduct(AddProductRequest request, Category category) {
        return  new Product(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getInventory(),
                request.getBrand(),
                category
        );
    }

    @Override
    public Product getProductById(long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @Override
    public void deleteProductById(long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete, () -> new ProductNotFoundException("Product not found"));
    }

    @Override
    public void updateProduct(Product product, long prodcutId) {

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getAllProductsByCategory(String category) {
        return productRepository.findAllByCategoryName(category);
    }

    @Override
    public List<Product> getAllProductsByBrand(String brand) {
        return productRepository.findAllByBrand(brand);
    }

    @Override
    public List<Product> getAllProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findAllByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getAllProductsByName(String name) {
        return productRepository.findAllByName(name);
    }

    @Override
    public List<Product> getAllProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countProductsByBrandAndName(brand, name);
    }
}
