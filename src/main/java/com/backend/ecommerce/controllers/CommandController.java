package com.backend.ecommerce.controllers;

import com.backend.ecommerce.entities.Command;
import com.backend.ecommerce.services.interfaces.CommandService;
import com.backend.ecommerce.utils.apiForm.ApiResponse;
import com.backend.ecommerce.utils.apiForm.ApiResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/commands")
@RequiredArgsConstructor
public class CommandController {

    private final CommandService commandService;
    private final ApiResponseService apiResponseService;

    @PostMapping("add-command")
    public ResponseEntity<ApiResponse> addCommand(@RequestBody Command command){
        return apiResponseService.createApiResponseForm(
                commandService.addCommand(command), true, HttpStatus.CREATED);
    }

    @DeleteMapping("remove/{idCommand}")
    public ResponseEntity<ApiResponse> removeCommand(@PathVariable Long idCommand){
        commandService.deleteCommand(idCommand);
        return apiResponseService.createApiResponseForm(null, true, HttpStatus.OK);
    }
}
