package com.ecommerce.app.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
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

/**
 * REST controller for managing users.
 *
 * Provides APIs for:
 * - creating users
 * - retrieving users
 * - updating users
 * - deleting users
 */

@RestController
@RequestMapping(
        value = "/api/v1/users",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {

    private final UserService userService;

    /**
     * Retrieves all users in paginated format.
     *
     * @param pageable pagination information
     * @return paginated user response
     */
    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @PageableDefault(
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.ASC
            )
            Pageable pageable
    ) {

        log.info(
                "action=getAllUsers status=request_received page={} size={} sort={}",
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort()
        );

        final Page<UserResponse> users =
                userService.getAllUsers(pageable);

        return ResponseEntity.ok(users);
    }

    /**
     * Creates a new user.
     *
     * @param userRequest validated request payload
     * @return created user response
     */
    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody UserRequest userRequest
    ) {

        log.info("action=createUser status=started");

        final UserResponse createdUser =
                userService.createUser(userRequest);

        log.info(
                "action=createUser status=success userId={}",
                createdUser.id()
        );

        URI location =
                URI.create("/api/v1/users/" + createdUser.id());

        return ResponseEntity
                .created(location)
                .body(createdUser);
    }

    /**
     * Retrieves user by identifier.
     *
     * @param userId unique user identifier
     * @return user details
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable @Positive Long userId
    ) {

        log.info(
                "action=getUserById status=request_received userId={}",
                userId
        );

        final UserResponse user =
                userService.getUserById(userId);

        return ResponseEntity.ok(user);
    }

    /**
     * Updates existing user.
     *
     * @param userId unique user identifier
     * @param userRequest updated request payload
     * @return updated user response
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable @Positive Long userId,
            @Valid @RequestBody UserRequest userRequest
    ) {

        log.info(
                "action=updateUser status=request_received userId={}",
                userId
        );

        final UserResponse updatedUser =
                userService.updateUser(userId, userRequest);

        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Deletes user by identifier.
     *
     * @param userId unique user identifier
     * @return no content response
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable @Positive Long userId
    ) {

        log.info(
                "action=deleteUser status=request_received userId={}",
                userId
        );

        userService.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }
}