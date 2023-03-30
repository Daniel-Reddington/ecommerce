package com.backend.ecommerce.controllers;

import com.backend.ecommerce.entities.AppUser;
import com.backend.ecommerce.services.interfaces.AppUserService;
import com.backend.ecommerce.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/users")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @PostMapping("add-user")
    public ResponseEntity<ApiResponse> addUser(@RequestBody AppUser appUser){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(appUserService.addUser(appUser));
        apiResponse.setSuccess(true);
        apiResponse.setStatus(HttpStatus.CREATED);

        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @PatchMapping("update-user")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody AppUser appUser){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(appUserService.updateUser(appUser));
        apiResponse.setSuccess(true);
        apiResponse.setStatus(HttpStatus.OK);

        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @PostMapping("add-role-to-user/{idUser}/{idRole}")
    public ResponseEntity<ApiResponse> addRoleToUser(@PathVariable String idUser, @PathVariable Integer idRole){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(appUserService.addRoleToUser(idUser, idRole));
        apiResponse.setSuccess(true);
        apiResponse.setStatus(HttpStatus.OK);

        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @GetMapping("find-all-user")
    public ResponseEntity<ApiResponse> findAllUser(){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(appUserService.findAllUser());
        apiResponse.setSuccess(true);
        apiResponse.setStatus(HttpStatus.FOUND);

        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @GetMapping("find-by-username-contains/{username}")
    public ResponseEntity<ApiResponse> findUserByUsernameContains(@PathVariable String username){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(appUserService.findUserByUsernameContains(username));
        apiResponse.setSuccess(true);
        apiResponse.setStatus(HttpStatus.FOUND);

        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @DeleteMapping("remove-user/{idUser}")
    public ResponseEntity<ApiResponse> removeUser(@PathVariable String idUser){
        appUserService.removeUserById(idUser);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSuccess(true);
        apiResponse.setStatus(HttpStatus.OK);

        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }
}
