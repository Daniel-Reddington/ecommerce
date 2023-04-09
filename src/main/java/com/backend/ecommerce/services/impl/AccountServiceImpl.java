package com.backend.ecommerce.services.impl;

import com.backend.ecommerce.dtos.UserAccountDto;
import com.backend.ecommerce.entities.AppUser;
import com.backend.ecommerce.services.interfaces.AccountService;
import com.backend.ecommerce.services.interfaces.AppUserService;
import com.backend.ecommerce.services.interfaces.FileService;
import com.backend.ecommerce.utils.constants.FileDirectory;
import com.backend.ecommerce.utils.mappers.AppUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AppUserMapper appUserMapper;
    private final AppUserService appUserService;
    private final FileService fileService;
    @Override
    public AppUser createAccount(UserAccountDto userAccountDto) {
        String uploadDir = FileDirectory.profilePictureDirectory;
        String profilePictureUrl = fileService.saveFile(userAccountDto.getProfilePicture(), uploadDir);
        AppUser appUser = appUserMapper.userAccountDtoToAppUser(userAccountDto);
        appUser.setProfilePictureUrl(profilePictureUrl);
        AppUser savedUser = appUserService.addUser(appUser);
        return savedUser;
    }

    @Override
    public void deleteAccount(String idUser) {
        AppUser appUser = appUserService.findUserById(idUser);
        appUserService.removeUserById(idUser);
        fileService.deleteFile(appUser.getProfilePictureUrl());
    }

}
