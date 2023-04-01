package com.backend.ecommerce.services.impl;

import com.backend.ecommerce.entities.AppRole;
import com.backend.ecommerce.exceptions.AppRoleNotFoundException;
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
    public AppRole saveRole(AppRole role) {
        if(role == null) throw new RuntimeException("Role is null");
        AppRole savedRole = appRoleRepository.save(role);
        return savedRole;
    }

    @Override
    public AppRole updateRole(AppRole role) {
        findRoleById(role.getId());
        return saveRole(role);
    }

    @Override
    public void removeRole(Integer idRole) {
        appRoleRepository.deleteById(idRole);
    }

    @Override
    public AppRole findRoleById(Integer idRole) {
        return appRoleRepository.findById(idRole).orElseThrow(()-> new AppRoleNotFoundException("Role not found"));
    }

    @Override
    public List<AppRole> findAllRole() {
        List<AppRole> appRoles = appRoleRepository.findAll();
        if (appRoles.size() == 0) throw new AppRoleNotFoundException("Nothing roles found");
        return appRoles;
    }

    @Override
    public List<AppRole> findAllByIds(HashSet<Integer> appRoleIds) {
        List<AppRole> appRoles = appRoleRepository.findAllById(appRoleIds);
        if (appRoles.size() == 0) throw new AppRoleNotFoundException("Nothing roles found");
        return appRoles;
    }
}
