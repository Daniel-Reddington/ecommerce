package com.backend.ecommerce.utils.deserializer;

import com.backend.ecommerce.entities.Category;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.beans.PropertyEditorSupport;
import java.io.IOException;

// bind category json to Category object
public class CategoryEditor extends PropertyEditorSupport {

    private final ObjectMapper objectMapper;

    public CategoryEditor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        try {
            Category category = objectMapper.readValue(text, Category.class);
            setValue(category);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
