package com.backend.ecommerce.utils.mappers;

import com.backend.ecommerce.dtos.ProductDto;
import com.backend.ecommerce.dtos.UserAccountDto;
import com.backend.ecommerce.entities.AppUser;
import com.backend.ecommerce.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product updateProduct(@MappingTarget Product product, Product currentProduct);

    @Mapping(target = "productImageUrl", ignore = true)
    Product productDtoToProduct(ProductDto productDto);


}

