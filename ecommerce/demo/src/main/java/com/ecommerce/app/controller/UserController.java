package com.ecommerce.app.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.app.dto.request.UserRequest;
import com.ecommerce.app.dto.response.UserResponse;
import com.ecommerce.app.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        log.info("action=getAllUsers status=started");
        List<UserResponse> users = userService.getAllUsers();
        log.info("action=getAllUsers status=success");
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest){
        log.info("action=createUser status=started");
        final UserResponse createdUser = userService.createUser(userRequest);
        log.info("action=createUser status=success");
        URI location = URI.create("/api/v1/users/" + createdUser.id());
        return ResponseEntity.created(location)
                .body(createdUser);

    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable @Positive Long userId){
        log.info("action=getUserById status=started userId={}", userId);
        final UserResponse user = userService.getUserById(userId);
        log.info("action=getUserById status=success userId={}", userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable @Positive Long userId, @Valid @RequestBody UserRequest userRequest){
        log.info("action=updateUser status=started userId={}",userId);
        final UserResponse updatedUser = userService.updateUser(userId, userRequest);
        log.info("action=updateUser status=success userId={}",userId);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable @Positive Long userId){
        log.info("action=deleteUser status=started userId={}", userId);
        userService.deleteUser(userId);
        log.info("action=deleteUser status=success userId={}", userId);
        return ResponseEntity.noContent().build();
    }
}
