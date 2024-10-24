package com.emmorce.dreamshops.service.product;

import com.emmorce.dreamshops.exceptions.ResourceNotFoundException;
import com.emmorce.dreamshops.model.Category;
import com.emmorce.dreamshops.model.Product;
import com.emmorce.dreamshops.repositoty.CategoryRepository;
import com.emmorce.dreamshops.repositoty.ProductRepository;
import com.emmorce.dreamshops.request.AddProductRequest;
import com.emmorce.dreamshops.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {


    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    
//    temporary addition

    @Override
    public Product addProduct(AddProductRequest request) {
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
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public void deleteProductById(long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete, () -> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository :: save)
                .orElseThrow( () -> new ResourceNotFoundException("Product not found") );
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setCategory(categoryRepository.findByName(request.getCategory().getName()));
        return productRepository.save(existingProduct);
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
