package com.rfs.app.controller;

import com.rfs.app.request.RegistrationRequest;
import com.rfs.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        userService.registerUser(registrationRequest);

        return ResponseEntity.ok("Registration Success");
    }

}
