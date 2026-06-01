package com.ecommerce.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ecommerce.app.dto.request.UserRequest;
import com.ecommerce.app.dto.response.UserResponse;

/**
 * Service interface for user management operations.
 */
public interface UserService {

    /**
     * Retrieves all users in paginated format.
     *
     * @param pageable pagination and sorting details
     * @return paginated user response
     */
    Page<UserResponse> getAllUsers(Pageable pageable);

    /**
     * Retrieves user by identifier.
     *
     * @param userId unique user identifier
     * @return user details
     */
    UserResponse getUserById(Long userId);

    /**
     * Creates a new user.
     *
     * @param userRequest validated user payload
     * @return created user details
     */
    UserResponse createUser(UserRequest userRequest);

    /**
     * Updates an existing user.
     *
     * @param userId unique user identifier
     * @param userRequest updated user payload
     * @return updated user details
     */
    UserResponse updateUser(
            Long userId,
            UserRequest userRequest
    );

    /**
     * Deletes user by identifier.
     *
     * @param userId unique user identifier
     */
    void deleteUser(Long userId);
}