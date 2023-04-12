package com.backend.ecommerce.utils.deserializer;

import com.backend.ecommerce.entities.AppRole;
import com.backend.ecommerce.entities.Category;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.util.*;

public class RoleEditor extends PropertyEditorSupport {

    private final ObjectMapper objectMapper;

    public RoleEditor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        try {
            AppRole[] roles = objectMapper.readValue(text, AppRole[].class);
            Set<AppRole> setRoles = new HashSet<>(Arrays.asList(roles));
            setValue(setRoles);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid app roles format");
        }

    }
}
