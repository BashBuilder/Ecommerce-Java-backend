package com.emmorce.dreamshops.controllers;

import com.emmorce.dreamshops.exceptions.AlreadyExistsException;
import com.emmorce.dreamshops.exceptions.ResourceNotFoundException;
import com.emmorce.dreamshops.model.Category;
import com.emmorce.dreamshops.response.ApiResponse;
import com.emmorce.dreamshops.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RequiredArgsConstructor
@RestController
@RequestMapping( "${api.prefix}/categories" )
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Categories fetched successfully", categories));
        } catch (Exception e) {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Eror:", INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category){
        try {
            Category savedCategory = categoryService.addCategory(category);
            return ResponseEntity.ok(new ApiResponse("Category saved successfully", savedCategory));
        } catch (AlreadyExistsException e) {
            return ResponseEntity
                   .status(CONFLICT)
                   .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> getCategoryById( @PathVariable Long categoryId) {
        try {
            Category category = categoryService.getCategoryById(categoryId);
            return ResponseEntity.ok(new ApiResponse("Category fetched successfully", category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                   .status(INTERNAL_SERVER_ERROR)
                   .body(new ApiResponse(e.getMessage(), e.getMessage()));
        }
    }


}
