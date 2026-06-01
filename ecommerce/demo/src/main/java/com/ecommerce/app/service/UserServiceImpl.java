package com.ecommerce.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.app.dto.request.UserRequest;
import com.ecommerce.app.dto.response.UserResponse;
import com.ecommerce.app.entity.User;
import com.ecommerce.app.exception.ResourceNotFoundException;
import com.ecommerce.app.mapper.UserMapper;
import com.ecommerce.app.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service implementation for user management operations.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    
    /**
     * Retrieves all users in paginated format.
     *
     * @param pageable pagination and sorting details
     * @return paginated user response
     */

    @Transactional(readOnly = true)
    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
    
        log.info(
            "action=getAllUsers status=started page={} size={}",
            pageable.getPageNumber(),
            pageable.getPageSize()
        );
    
        Page<UserResponse> users =
                userRepository.findAll(pageable)
                        .map(UserMapper::mapToUserResponse);
    
        log.info(
            "action=getAllUsers status=success totalElements={} totalPages={}",
            users.getTotalElements(),
            users.getTotalPages()
        );
    
        return users;
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponse getUserById(Long userId) {
        User fetchedUser = getUserEntity(userId);
        log.info(
            "action=getUserById status=success userId={}",
            fetchedUser.getId()
        );
        
        return UserMapper.mapToUserResponse(fetchedUser);
    }

    @Transactional
    @Override
    public UserResponse createUser(UserRequest userRequest) {        
        log.info(
            "action=createUser status=started email={}",
            userRequest.email()
        );

        User user = UserMapper.mapToUser(userRequest);
        User savedUser = userRepository.save(user);
        log.info(
            "action=createUser status=success userId={}",
            savedUser.getId()
        );
        
        return UserMapper.mapToUserResponse(savedUser);
    }

    @Transactional
    @Override
    public UserResponse updateUser(
        Long userId, 
        UserRequest userRequest
    ) {
        User existingUser = getUserEntity(userId);
        log.info(
            "action=updateUser status=started userId={}",
            userId
        );
        
        existingUser.setName(userRequest.name());
        existingUser.setEmail(userRequest.email());
        existingUser.setPhone(userRequest.phone());
        existingUser.setAddress(userRequest.address());
        User savedUser = userRepository.save(existingUser);
        log.info(
            "action=updateUser status=success userId={}",
            userId
        );
        
        return UserMapper.mapToUserResponse(savedUser);
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        User existingUser = getUserEntity(userId);
        log.info(
            "action=deleteUser status=started userId={}",
            userId
        );
        
        userRepository.delete(existingUser);
        log.info(
            "action=deleteUser status=success userId={}",
            userId
        );
        
    }

    private User getUserEntity(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()->{
                    log.warn(
                        "action=getUserEntity status=failed reason=user_not_found userId={}",
                        userId
                    );

                    return new ResourceNotFoundException(String.format("User not found with id %s",userId));
                    }
                );
    }
    
}
