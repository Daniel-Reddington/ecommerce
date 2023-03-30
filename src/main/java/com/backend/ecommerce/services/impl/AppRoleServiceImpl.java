package com.backend.ecommerce.services.impl;

import com.backend.ecommerce.entities.AppRole;
import com.backend.ecommerce.repositories.AppRoleRepository;
import com.backend.ecommerce.services.interfaces.AppRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AppRoleServiceImpl implements AppRoleService {

    private final AppRoleRepository appRoleRepository;

    @Override
    public AppRole addRole(AppRole role) {
        AppRole savedRole = appRoleRepository.save(role);
        return savedRole;
    }

    @Override
    public AppRole updateRole(AppRole role) {
        return addRole(role);
    }

    @Override
    public void removeRole(Integer idRole) {
        appRoleRepository.deleteById(idRole);
    }

    @Override
    public AppRole findRoleById(Integer idRole) {
        return appRoleRepository.findById(idRole).orElseThrow(()-> new RuntimeException("Role not found"));
    }

    @Override
    public List<AppRole> findAllRole() {
        return appRoleRepository.findAll();
    }

    @Override
    public List<AppRole> findAllByIds(HashSet<Integer> appRoleIds) {
        return appRoleRepository.findAllById(appRoleIds);
    }
}
