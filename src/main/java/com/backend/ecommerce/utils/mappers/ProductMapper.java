package com.backend.ecommerce.utils.mappers;

import com.backend.ecommerce.entities.Product;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product updateProduct(@MappingTarget Product product, Product currentProduct);

}

