package com.backend.ecommerce.services.interfaces;

import com.backend.ecommerce.entities.AppRole;

import java.util.HashSet;
import java.util.List;

public interface AppRoleService {

    AppRole addRole(AppRole role);
    AppRole updateRole(AppRole role);
    void removeRole(Integer idRole);
    AppRole findRoleById(Integer idRole);
    List<AppRole> findAllRole();
    List<AppRole> findAllByIds(HashSet<Integer> appRoleIds);

}
