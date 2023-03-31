package com.backend.ecommerce.controllers;

import com.backend.ecommerce.entities.AppRole;
import com.backend.ecommerce.services.interfaces.AppRoleService;
import com.backend.ecommerce.utils.apiForm.ApiResponse;
import com.backend.ecommerce.utils.apiForm.ApiResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AppRoleController {

    private final AppRoleService appRoleService;
    private final ApiResponseService apiResponseService;

    @PostMapping("save-role")
    public ResponseEntity<ApiResponse> saveRole(@RequestBody AppRole appRole){
        return apiResponseService.createApiResponseForm(appRoleService.saveRole(appRole),true,HttpStatus.CREATED);
    }

    @PutMapping("update-role")
    public ResponseEntity<ApiResponse> updateRole(@RequestBody AppRole appRole){
        return apiResponseService.createApiResponseForm(appRoleService.updateRole(appRole),true,HttpStatus.OK);
    }

    @DeleteMapping("remove-role/{idRole}")
    public ResponseEntity<ApiResponse> removeRole(@PathVariable Integer idRole){
        appRoleService.removeRole(idRole);
        return apiResponseService.createApiResponseForm(null, true, HttpStatus.OK);
    }

    @GetMapping("find-all-role")
    public ResponseEntity<ApiResponse> findAllRole(){
        return apiResponseService.createApiResponseForm(appRoleService.findAllRole(), true, HttpStatus.OK);
    }

    @GetMapping("find-role-by-id/{idRole}")
    public ResponseEntity<ApiResponse> findRoleById(@PathVariable Integer idRole){
        return apiResponseService.createApiResponseForm(appRoleService.findRoleById(idRole), true, HttpStatus.OK);
    }

}
