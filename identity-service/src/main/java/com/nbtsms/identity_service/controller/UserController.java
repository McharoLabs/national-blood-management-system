package com.nbtsms.identity_service.controller;

import com.nbtsms.identity_service.dto.CreateUserDTO;
import com.nbtsms.identity_service.exception.ConflictException;
import com.nbtsms.identity_service.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/user/exists/{id}")
    public boolean isUserPreset(@PathVariable("id") UUID id) {
        return userService.isUserPreset(id);
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        Map<String, String> response = new HashMap<>();

        try {
            String responseMessage = userService.create(createUserDTO);
            response.put("message", responseMessage);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ConflictException e) {
            response.putAll(e.getErrorMessages());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
