package com.backend.ecommerce.services.interfaces;

import com.backend.ecommerce.dtos.UserAccountDto;
import com.backend.ecommerce.entities.AppUser;

public interface AccountService {

    AppUser createAccount(UserAccountDto userAccountDto);
    void deleteAccount(String idUser);

}
