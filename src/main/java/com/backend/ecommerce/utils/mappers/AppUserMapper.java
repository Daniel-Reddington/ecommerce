package com.backend.ecommerce.utils.mappers;

import com.backend.ecommerce.dtos.UserAccountDto;
import com.backend.ecommerce.entities.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public interface AppUserMapper {

    AppUserMapper INSTANCE = Mappers.getMapper(AppUserMapper.class);

    AppUser updateAppUser(@MappingTarget AppUser appUser, AppUser currentAppUser);
    @Mapping(target = "profilePictureUrl", ignore = true)
    AppUser userAccountDtoToAppUser(UserAccountDto userAccountDto);

}
