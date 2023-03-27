package com.backend.ecommerce.services.interfaces;

import com.backend.ecommerce.entities.Category;
import com.backend.ecommerce.entities.Product;

import java.util.List;

public interface CategoryService {
    Category addCategory(Category category);
    Category updateCategory(Category category);
    void removeCategory(Integer idCategory);
    Category findCategoryById(Integer idCategory);
    List<Category> findAllCategory();

    List<Product> findAllProductInCategory(Integer idCategory);
}
