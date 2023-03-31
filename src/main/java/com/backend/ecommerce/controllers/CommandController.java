package com.backend.ecommerce.controllers;

import com.backend.ecommerce.entities.Command;
import com.backend.ecommerce.services.interfaces.CommandService;
import com.backend.ecommerce.utils.apiForm.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/commands")
@RequiredArgsConstructor
public class CommandController {

    private final CommandService commandService;

    @PostMapping("add-command")
    public ResponseEntity<ApiResponse> addCommand(@RequestBody Command command){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(commandService.addCommand(command));
        apiResponse.setSuccess(true);
        apiResponse.setStatus(HttpStatus.CREATED);

        return new ResponseEntity<>(apiResponse,apiResponse.getStatus());
    }

    @DeleteMapping("remove/{idCommand}")
    public ResponseEntity<ApiResponse> removeCommand(@PathVariable Long idCommand){
        commandService.deleteCommand(idCommand);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSuccess(true);
        apiResponse.setStatus(HttpStatus.OK);

        return new ResponseEntity<>(apiResponse,apiResponse.getStatus());
    }
}
