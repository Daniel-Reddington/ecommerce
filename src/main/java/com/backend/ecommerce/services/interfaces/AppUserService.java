package com.backend.ecommerce.services.interfaces;

import com.backend.ecommerce.entities.AppUser;

import java.util.List;

public interface AppUserService {

    AppUser addUser(AppUser appUser);
    AppUser updateUser(AppUser appUser);
    AppUser addRoleToUser(String idUser, Integer idRole);
    List<AppUser> findAllUser();
    List<AppUser> findUserByUsernameContains(String username);
    AppUser findUserById(String idUser);

    void removeUserById(String idUser);

}
