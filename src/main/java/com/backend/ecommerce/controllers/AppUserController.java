package com.backend.ecommerce.controllers;

import com.backend.ecommerce.dtos.UserAccountDto;
import com.backend.ecommerce.entities.AppUser;
import com.backend.ecommerce.services.interfaces.AccountService;
import com.backend.ecommerce.services.interfaces.AppUserService;
import com.backend.ecommerce.utils.apiForm.ApiResponse;
import com.backend.ecommerce.utils.apiForm.ApiResponseService;
import com.backend.ecommerce.validator.AddMethodValidator;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "api/users")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;
    private final ApiResponseService apiResponseService;
    private final AccountService accountService;

    @PostMapping("create-account")
    public ResponseEntity<ApiResponse> addUser(@Validated(AddMethodValidator.class) @RequestBody UserAccountDto userAccountDto){
        return apiResponseService.createApiResponseForm(accountService.createAccount(userAccountDto), true, HttpStatus.CREATED);
    }

    @PatchMapping("update-user")
    @PreAuthorize("hasAnyRole('SCOPE_USER', 'SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse> updateUser(@Validated @RequestBody AppUser appUser){
        return apiResponseService.createApiResponseForm(appUserService.updateUser(appUser), true, HttpStatus.OK);
    }

    @PostMapping("add-role-to-user/{idUser}/{idRole}")
    @PreAuthorize("hasAnyRole('SCOPE_USER', 'SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse> addRoleToUser(@PathVariable String idUser, @PathVariable Integer idRole){
        return apiResponseService.createApiResponseForm(
                appUserService.addRoleToUser(idUser, idRole), true, HttpStatus.OK);
    }

    @GetMapping("find-all-user")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse> findAllUser(){
        return apiResponseService.createApiResponseForm(appUserService.findAllUser(), true, HttpStatus.OK);
    }

    @GetMapping("find-by-username-contains/{username}")
    @PreAuthorize("hasAnyRole('SCOPE_USER', 'SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse> findUserByUsernameContains(@PathVariable String username){
        return apiResponseService.createApiResponseForm(
                appUserService.findUserByUsernameContains(username), true, HttpStatus.OK);
    }

    @DeleteMapping("remove-user/{idUser}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse> removeUser(@PathVariable String idUser){
        accountService.deleteAccount(idUser);
        return apiResponseService.createApiResponseForm(null, true, HttpStatus.OK);
    }
}
