package com.backend.ecommerce.services.impl;

import com.backend.ecommerce.entities.AppRole;
import com.backend.ecommerce.exceptions.AppRoleNotFoundException;
import com.backend.ecommerce.repositories.AppRoleRepository;
import com.backend.ecommerce.services.interfaces.AppRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
    public AppRole findByRoleName(String roleName) {
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        if (appRole == null) throw new AppRoleNotFoundException("Role not found");
        return appRole;
    }

    @Override
    public Set<AppRole> findAllRole() {
        List<AppRole> appRoles = appRoleRepository.findAll();
        if (appRoles.size() == 0) throw new AppRoleNotFoundException("Nothing roles found");
        return new LinkedHashSet<>(appRoles);
    }

    @Override
    public Set<AppRole> findAllByIds(Set<Integer> appRoleIds) {
        List<AppRole> appRoles = appRoleRepository.findAllById(appRoleIds);
        if (appRoles.size() == 0) throw new AppRoleNotFoundException("Nothing roles found");
        return new LinkedHashSet<>(appRoles);
    }

}
