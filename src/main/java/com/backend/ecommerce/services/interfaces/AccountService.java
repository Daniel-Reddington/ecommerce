package com.backend.ecommerce.services.interfaces;

import com.backend.ecommerce.dtos.UserAccountDto;
import com.backend.ecommerce.entities.AppUser;
import org.springframework.web.multipart.MultipartFile;

public interface AccountService {

    AppUser createAccount(AppUser appUser, MultipartFile profilePicture);
    AppUser updateAccount(AppUser appUser);
    boolean updatePassword(String idUser, String oldPassword, String currentPassword);
    void deleteAccount(String idUser);

}
