package com.mykare.in.controller;

import com.mykare.in.request.UserRegistrationRequest;
import com.mykare.in.service.UserService;
import com.mykare.in.utils.CommonFunctionUtils;
import com.mykare.in.utils.DataValidations;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User Management Controller", description = "Handles user-related operations such as registration, login, and deletion.")
public class UserController {

    private final UserService userSvc;

    private final CommonFunctionUtils commonFunctionUtils;

    @Operation(summary = "Create a new user")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest)
    {
        return ResponseEntity.ok().body(userSvc.userRegistration(userRegistrationRequest));
    }

    @Operation(summary = "User login")
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserRegistrationRequest userRegistrationRequest)
    {
        return ResponseEntity.ok().body(userSvc.userLogin(userRegistrationRequest));
    }

    @Operation(summary = "Get list of all Users")
    @GetMapping("/details")
    public ResponseEntity<?> getAllRegisteredUsers(HttpServletRequest request) {
        String[] credentials = commonFunctionUtils.extractCredentials(request);
        if (credentials == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userName = credentials[0];
        String password = credentials[1];

        if (commonFunctionUtils.isValidUser(userName, password)) {

            return ResponseEntity.ok().body(userSvc.getAllRegisteredUsers(userName));
        }
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @Operation(summary = "Delete an user")
    @PostMapping("/delete/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable("email") String email,HttpServletRequest request)
    {
        String[] credentials = commonFunctionUtils.extractCredentials(request);
        if (credentials == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userName = credentials[0];
        String password = credentials[1];

        if (commonFunctionUtils.isValidUser(userName, password)) {

            return ResponseEntity.ok().body(userSvc.deleteUserByEmail(userName,email));
        }
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }




}
