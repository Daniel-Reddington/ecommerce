package com.backend.ecommerce.services.impl;

import com.backend.ecommerce.entities.Category;
import com.backend.ecommerce.entities.Product;
import com.backend.ecommerce.exceptions.CategoryNotFoundException;
import com.backend.ecommerce.exceptions.ProductNotFoundException;
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
        if (category == null) throw new CategoryNotFoundException("Category is null");
        category= categoryRepository.save(category);

        return category;
    }

    @Override
    public Category updateCategory(Category category) {
        findCategoryById(category.getId());
        return addCategory(category);
    }

    @Override
    public void removeCategory(Integer idCategory) {
        categoryRepository.deleteById(idCategory);
    }

    @Override
    public Category findCategoryById(Integer idCategory) {
        Category category = categoryRepository.findById(idCategory).orElseThrow(()->
                new CategoryNotFoundException("Category of id '"+idCategory+"' not found"));
        return category;
    }

    @Override
    public List<Category> findAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        if(categories.size() == 0) throw new CategoryNotFoundException("Nothing categories found");
        return categories;
    }

    @Override
    public List<Product> findAllProductInCategory(Integer idCategory) {
        Category category = findCategoryById(idCategory);
        List<Product> products = category.getProducts();
        if(products.isEmpty()) throw new ProductNotFoundException("Nothing products found for this category");
        return products;
    }

}
