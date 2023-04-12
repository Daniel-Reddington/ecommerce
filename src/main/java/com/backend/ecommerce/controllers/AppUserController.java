package com.backend.ecommerce.controllers;

import com.backend.ecommerce.entities.AppUser;
import com.backend.ecommerce.services.interfaces.AccountService;
import com.backend.ecommerce.services.interfaces.AppUserService;
import com.backend.ecommerce.utils.apiForm.ApiResponse;
import com.backend.ecommerce.utils.apiForm.ApiResponseService;
import com.backend.ecommerce.utils.deserializer.RoleEditor;
import com.backend.ecommerce.validator.AddMethodValidator;
import com.backend.ecommerce.validator.UpdateMethodValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping(path = "api/users")
@RequiredArgsConstructor
@Tag(name = "User")
public class AppUserController {

    private final AppUserService appUserService;
    private final ApiResponseService apiResponseService;
    private final AccountService accountService;
    private final ObjectMapper objectMapper;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Set.class, "appRoles", new RoleEditor(objectMapper));
    }

    @PostMapping(value = "create-account", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponse> createAccount(@Validated(AddMethodValidator.class) @RequestPart AppUser appUser,
                                                     @RequestPart MultipartFile profilePicture){
        return apiResponseService.createApiResponseForm(accountService.createAccount(appUser, profilePicture), true, HttpStatus.CREATED);
    }

    @PatchMapping("update-account")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER', 'SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse> updateAccount(@Validated(UpdateMethodValidator.class) @RequestBody AppUser appUser){
        return apiResponseService.createApiResponseForm(accountService.updateAccount(appUser), true, HttpStatus.OK);
    }

    @PutMapping("update-password")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER', 'SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse> updatePassword(@RequestParam String idUser,
                                                      @RequestParam String oldPassword,
                                                      @RequestParam String currentPassword){
        return apiResponseService.createApiResponseForm(
                accountService.updatePassword(idUser, oldPassword, currentPassword), true, HttpStatus.OK);
    }

    @PostMapping("add-role-to-user/{idUser}/{idRole}")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER', 'SCOPE_ADMIN')")
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
    @PreAuthorize("hasAnyAuthority('SCOPE_USER', 'SCOPE_ADMIN')")
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
