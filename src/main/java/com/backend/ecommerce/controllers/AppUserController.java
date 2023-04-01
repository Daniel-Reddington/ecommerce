package com.backend.ecommerce.controllers;

import com.backend.ecommerce.entities.AppUser;
import com.backend.ecommerce.services.interfaces.AppUserService;
import com.backend.ecommerce.utils.apiForm.ApiResponse;
import com.backend.ecommerce.utils.apiForm.ApiResponseService;
import com.backend.ecommerce.validator.AddMethodValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "api/users")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;
    private final ApiResponseService apiResponseService;

    @PostMapping("add-user")
    public ResponseEntity<ApiResponse> addUser(@Validated(AddMethodValidator.class) @RequestBody AppUser appUser){
        return apiResponseService.createApiResponseForm(appUserService.addUser(appUser), true, HttpStatus.CREATED);
    }

    @PatchMapping("update-user")
    public ResponseEntity<ApiResponse> updateUser(@Validated @RequestBody AppUser appUser){
        return apiResponseService.createApiResponseForm(appUserService.updateUser(appUser), true, HttpStatus.OK);
    }

    @PostMapping("add-role-to-user/{idUser}/{idRole}")
    public ResponseEntity<ApiResponse> addRoleToUser(@PathVariable String idUser, @PathVariable Integer idRole){
        return apiResponseService.createApiResponseForm(
                appUserService.addRoleToUser(idUser, idRole), true, HttpStatus.OK);
    }

    @GetMapping("find-all-user")
    public ResponseEntity<ApiResponse> findAllUser(){
        return apiResponseService.createApiResponseForm(appUserService.findAllUser(), true, HttpStatus.OK);
    }

    @GetMapping("find-by-username-contains/{username}")
    public ResponseEntity<ApiResponse> findUserByUsernameContains(@PathVariable String username){
        return apiResponseService.createApiResponseForm(
                appUserService.findUserByUsernameContains(username), true, HttpStatus.OK);
    }

    @DeleteMapping("remove-user/{idUser}")
    public ResponseEntity<ApiResponse> removeUser(@PathVariable String idUser){
        appUserService.removeUserById(idUser);
        return apiResponseService.createApiResponseForm(null, true, HttpStatus.OK);
    }
}
