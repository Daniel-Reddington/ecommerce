package com.backend.ecommerce.services.interfaces;

import com.backend.ecommerce.entities.Category;

import java.util.List;

public interface CategoryService {
    Category addCategory(Category category);
    Category updateCategory(Category category);
    void removeCategory(Integer idCategory);
    Category findCategoryById(Integer idCategory);
    List<Category> findAllCategory();
}
