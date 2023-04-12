package com.backend.ecommerce.controllers;

import com.backend.ecommerce.dtos.UserAccountDto;
import com.backend.ecommerce.entities.AppRole;
import com.backend.ecommerce.entities.AppUser;
import com.backend.ecommerce.services.interfaces.AccountService;
import com.backend.ecommerce.services.interfaces.AppUserService;
import com.backend.ecommerce.utils.apiForm.ApiResponse;
import com.backend.ecommerce.utils.apiForm.ApiResponseService;
import com.backend.ecommerce.utils.deserializer.RoleEditor;
import com.backend.ecommerce.validator.AddMethodValidator;
import com.backend.ecommerce.validator.UpdateMethodValidator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.util.List;


@RestController
@RequestMapping(path = "api/users")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;
    private final ApiResponseService apiResponseService;
    private final AccountService accountService;
    private final ObjectMapper objectMapper;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Set.class, "appRoles", new RoleEditor(objectMapper));
    }

    @Bean
    public MappingJackson2HttpMessageConverter octetStreamJsonConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(List.of(new MediaType("application", "octet-stream")));
        return converter;
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
