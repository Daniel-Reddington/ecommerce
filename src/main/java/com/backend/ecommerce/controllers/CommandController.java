package com.backend.ecommerce.controllers;

import com.backend.ecommerce.entities.Command;
import com.backend.ecommerce.services.interfaces.CommandService;
import com.backend.ecommerce.utils.apiForm.ApiResponse;
import com.backend.ecommerce.utils.apiForm.ApiResponseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/commands")
@RequiredArgsConstructor
@Tag(name = "Command")
public class CommandController {

    private final CommandService commandService;
    private final ApiResponseService apiResponseService;

    @PostMapping("add-command")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER', 'SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse> addCommand(@Validated @RequestBody Command command){
        return apiResponseService.createApiResponseForm(
                commandService.addCommand(command), true, HttpStatus.CREATED);
    }

    @DeleteMapping("remove/{idCommand}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse> removeCommand(@PathVariable Long idCommand){
        commandService.deleteCommand(idCommand);
        return apiResponseService.createApiResponseForm(null, true, HttpStatus.OK);
    }

}
