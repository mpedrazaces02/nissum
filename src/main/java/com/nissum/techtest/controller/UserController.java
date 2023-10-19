package com.nissum.techtest.controller;

import com.nissum.techtest.dto.CreateUserRequestDTO;
import com.nissum.techtest.common.APIResponse;
import com.nissum.techtest.dto.CreatedUserResponseDTO;
import com.nissum.techtest.dto.UserResponseDTO;
import com.nissum.techtest.exception.EmailException;
import com.nissum.techtest.exception.PasswordException;
import com.nissum.techtest.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "users/v1")
@Api(value = "UserController", tags = { "User Controller" })
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Creates an user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CreatedUserResponseDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = APIResponse.class),
            @ApiResponse(code = 400, message = "Error", response = APIResponse.class)})
    @PatchMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CreatedUserResponseDTO> createUser(
            @RequestBody final CreateUserRequestDTO userDTO) throws PasswordException, EmailException {

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(userService.createUser(userDTO));

    }


    @ApiOperation(value = "Gets the complete list of users.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserResponseDTO[].class),
            @ApiResponse(code = 401, message = "Unauthorized", response = APIResponse.class),
            @ApiResponse(code = 400, message = "Error", response = APIResponse.class)})
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<UserResponseDTO>> getEncuestas() {
            return ResponseEntity.ok(userService.getUsers());
    }

}
