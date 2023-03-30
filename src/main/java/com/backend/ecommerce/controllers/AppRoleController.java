package com.backend.ecommerce.controllers;

import com.backend.ecommerce.entities.AppRole;
import com.backend.ecommerce.services.interfaces.AppRoleService;
import com.backend.ecommerce.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AppRoleController {

    private AppRoleService appRoleService;

    @PostMapping("save-role")
    public ResponseEntity<ApiResponse> saveRole(@RequestBody AppRole appRole){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(appRoleService.saveRole(appRole));
        apiResponse.setSuccess(true);
        apiResponse.setStatus(HttpStatus.CREATED);

        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @PutMapping("update-role")
    public ResponseEntity<ApiResponse> updateRole(@RequestBody AppRole appRole){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(appRoleService.updateRole(appRole));
        apiResponse.setSuccess(true);
        apiResponse.setStatus(HttpStatus.OK);

        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @DeleteMapping("remove-role/{idRole}")
    public ResponseEntity<ApiResponse> removeRole(@PathVariable Integer idRole){
        appRoleService.removeRole(idRole);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSuccess(true);
        apiResponse.setStatus(HttpStatus.OK);

        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @GetMapping("find-all-role")
    public ResponseEntity<ApiResponse> findAllRole(){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(appRoleService.findAllRole());
        apiResponse.setSuccess(true);
        apiResponse.setStatus(HttpStatus.FOUND);

        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @GetMapping("find-role-by-id/{idRole}")
    public ResponseEntity<ApiResponse> findRoleById(@PathVariable Integer idRole){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(appRoleService.findRoleById(idRole));
        apiResponse.setSuccess(true);
        apiResponse.setStatus(HttpStatus.FOUND);

        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

}
