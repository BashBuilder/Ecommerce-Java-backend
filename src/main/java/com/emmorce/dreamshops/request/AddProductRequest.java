package com.emmorce.dreamshops.request;

import com.emmorce.dreamshops.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddProductRequest {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int inventory;
    private String brand;
    private Category category;
}
