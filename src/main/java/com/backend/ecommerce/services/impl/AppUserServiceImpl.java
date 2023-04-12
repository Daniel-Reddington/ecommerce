package com.backend.ecommerce.services.impl;

import com.backend.ecommerce.entities.AppRole;
import com.backend.ecommerce.entities.AppUser;
import com.backend.ecommerce.exceptions.AppUserNotFoundException;
import com.backend.ecommerce.repositories.AppUserRepository;
import com.backend.ecommerce.services.interfaces.AppRoleService;
import com.backend.ecommerce.services.interfaces.AppUserService;
import com.backend.ecommerce.utils.mappers.AppUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final AppRoleService appRoleService;
    private final AppUserMapper appUserMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUser addUser(AppUser appUser) {

        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        Set<Integer> appRoleIds = new LinkedHashSet<>();
        appUser.getAppRoles().forEach(role -> {
            appRoleIds.add(role.getId());
        });

        appUser.setAppRoles(appRoleService.findAllByIds(appRoleIds));
        appUser.setCreatedAt(LocalDateTime.now());
        appUser.setUpdatedAt(LocalDateTime.now());
        AppUser savedUser = appUserRepository.save(appUser);
        return savedUser;
    }

    @Override
    public AppUser updateUser(AppUser currentUser) {
        System.out.println("updateuser");
        System.out.println(currentUser);
        AppUser appUser = findUserById(currentUser.getId());
        System.out.println("appuser");
        System.out.println(appUser);
        AppUser updatedUser = appUserMapper.updateAppUser(appUser, currentUser);
        System.out.println("updateduser");
        System.out.println(updatedUser);
        updatedUser.setUpdatedAt(LocalDateTime.now());
        return appUserRepository.save(updatedUser);
    }

    @Override
    public AppUser addRoleToUser(String idUser, Integer idRole) {
        AppUser appUser = findUserById(idUser);
        appUser.getAppRoles().add(AppRole.builder().id(idRole).build());
        AppUser savedUser = addUser(appUser);
        return savedUser;
    }

    @Override
    public List<AppUser> findAllUser() {
        List<AppUser> appUsers = appUserRepository.findAll();
        if(appUsers.size() == 0) throw new AppUserNotFoundException("Nothing users found");
        return appUsers;
    }

    @Override
    public AppUser loadUserByUserName(String username) {
        AppUser appUser = appUserRepository.findByUsername(username);
        return appUser;
    }

    @Override
    public List<AppUser> findUserByUsernameContains(String username) {
        List<AppUser> appUsers = appUserRepository.findByUsernameContainsIgnoreCase(username);
        if(appUsers.size() == 0) throw new AppUserNotFoundException("Nothing users with username contains "+username);
        return appUsers;
    }

    @Override
    public AppUser findUserById(String idUser) {
        return appUserRepository.findById(idUser).orElseThrow(()-> new AppUserNotFoundException("User not found"));
    }

    @Override
    public void removeUserById(String idUser) {
        appUserRepository.deleteById(idUser);
    }

}
