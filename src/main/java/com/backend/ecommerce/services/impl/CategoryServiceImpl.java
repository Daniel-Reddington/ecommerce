package com.backend.ecommerce.services.impl;

import com.backend.ecommerce.entities.Category;
import com.backend.ecommerce.repositories.CategoryRepository;
import com.backend.ecommerce.services.interfaces.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public Category addCategory(Category category) {
        if (category == null) throw new RuntimeException("Category is null");
        category= categoryRepository.save(category);

        return category;
    }

    @Override
    public Category updateCategory(Category category) {
        return addCategory(category);
    }

    @Override
    public void removeCategory(Integer idCategory) {
        if (idCategory == null) throw new RuntimeException("Id of category is null");
        categoryRepository.deleteById(idCategory);
    }

    @Override
    public Category findCategoryById(Integer idCategory) {
        Category category = categoryRepository.findById(idCategory).orElse(null);
        if(category == null) throw new RuntimeException("User not found");

        return category;
    }

    @Override
    public List<Category> findAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }
}
