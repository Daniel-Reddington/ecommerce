package com.backend.ecommerce.services.impl;

import com.backend.ecommerce.dtos.UserAccountDto;
import com.backend.ecommerce.entities.AppUser;
import com.backend.ecommerce.services.interfaces.AccountService;
import com.backend.ecommerce.services.interfaces.AppUserService;
import com.backend.ecommerce.services.interfaces.FileService;
import com.backend.ecommerce.utils.constants.FileDirectory;
import com.backend.ecommerce.utils.mappers.AppUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AppUserMapper appUserMapper;
    private final AppUserService appUserService;
    private final FileService fileService;
    private final PasswordEncoder passwordEncoder;
    private static final String uploadDir = FileDirectory.profilePictureDirectory;
    @Override
    public AppUser createAccount(AppUser appUser, MultipartFile profilePicture) {

        String profilePictureUrl = fileService.saveFile(profilePicture, uploadDir);
        appUser.setProfilePictureUrl(profilePictureUrl);
        AppUser savedUser = appUserService.addUser(appUser);
        return savedUser;
    }

    @Override
    public AppUser updateAccount(AppUser currentUser) {
        System.out.println("updateaccount");
        System.out.println(currentUser);
        AppUser savedUser = appUserService.updateUser(currentUser);
        return savedUser;
    }

    @Override
    public boolean updatePassword(String idUser, String oldPassword, String currentPassword){
        AppUser appUser = appUserService.findUserById(idUser);
        if(isPasswordHashed(oldPassword, appUser.getPassword())){
            appUser.setPassword(passwordEncoder.encode(currentPassword));
            return true;
        }
        return false;
    }

    public boolean isPasswordHashed(String password, String hashedPassword){
        return passwordEncoder.matches(password, hashedPassword);
    }

    @Override
    public void deleteAccount(String idUser) {
        AppUser appUser = appUserService.findUserById(idUser);
        appUserService.removeUserById(idUser);
        fileService.deleteFile(appUser.getProfilePictureUrl());
    }

}
