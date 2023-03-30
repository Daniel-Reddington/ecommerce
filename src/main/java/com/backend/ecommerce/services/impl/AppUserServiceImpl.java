package com.backend.ecommerce.services.impl;

import com.backend.ecommerce.entities.AppRole;
import com.backend.ecommerce.entities.AppUser;
import com.backend.ecommerce.repositories.AppUserRepository;
import com.backend.ecommerce.services.interfaces.AppRoleService;
import com.backend.ecommerce.services.interfaces.AppUserService;
import com.backend.ecommerce.utils.mappers.AppUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final AppRoleService appRoleService;
    private final AppUserMapper appUserMapper;

    @Override
    public AppUser addUser(AppUser appUser) {

        appUser.setId(UUID.randomUUID().toString());

        HashSet<Integer> appRoleIds = new HashSet<>();
        appUser.getAppRoles().forEach(role -> {
            appRoleIds.add(role.getId());
        });

        appUser.setAppRoles(appRoleService.findAllByIds(appRoleIds));
        appUser.setCreatedAt(LocalDateTime.now());
        AppUser savedUser = appUserRepository.save(appUser);
        return savedUser;
    }

    @Override
    public AppUser updateUser(AppUser currentAppUser) {
        AppUser appUser = appUserRepository.findById(currentAppUser.getId()).orElse(null);
        if(appUser == null) throw new RuntimeException("User not found");
        appUser.setUpdatedAt(LocalDateTime.now());
        AppUser updatedAppUser = appUserMapper.updateAppUser(appUser, currentAppUser);
        return appUserRepository.save(updatedAppUser);
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
        return appUserRepository.findAll();
    }

    @Override
    public List<AppUser> findUserByUsernameContains(String username) {
        return appUserRepository.findByUsernameContains(username);
    }

    @Override
    public AppUser findUserById(String idUser) {
        return appUserRepository.findById(idUser).orElseThrow(()-> new RuntimeException("User not found"));
    }

    @Override
    public void removeUserById(String idUser) {
        appUserRepository.deleteById(idUser);
    }
}
