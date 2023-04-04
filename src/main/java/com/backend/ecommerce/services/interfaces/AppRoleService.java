package com.backend.ecommerce.services.interfaces;

import com.backend.ecommerce.entities.AppRole;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface AppRoleService {

    AppRole saveRole(AppRole role);
    AppRole updateRole(AppRole role);
    void removeRole(Integer idRole);
    AppRole findRoleById(Integer idRole);
    Set<AppRole> findAllRole();
    Set<AppRole> findAllByIds(Set<Integer> appRoleIds);

}
