package com.backend.ecommerce.utils.mappers;

import com.backend.ecommerce.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public interface ProductMapper {

    //ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product updateProduct(@MappingTarget Product product, Product currentProduct);

}

