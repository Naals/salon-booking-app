package com.project.userservice.service.impl;

import com.project.userservice.exception.UserException;
import com.project.userservice.modal.User;
import com.project.userservice.payload.dto.KeycloakUserDTO;
import com.project.userservice.repository.UserRepository;
import com.project.userservice.service.KeycloakService;
import com.project.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final KeycloakService keycloakService;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) throws UserException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found with id: " + id));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) throws UserException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found with id: " + id));
        userRepository.delete(user);
    }

    @Override
    public User updateUser(Long id, User updatedUser) throws UserException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found"));

        user.setFullName(updatedUser.getFullName());
        user.setEmail(updatedUser.getEmail());
        user.setPhone(updatedUser.getPhone());

        return userRepository.save(user);
    }

    @Override
    public User getUserByJwtToken(String jwt) throws Exception {
        KeycloakUserDTO dto = keycloakService.fetchUserProfileByJwt(jwt);
        return userRepository.findByUsername(dto.getUsername());
    }
}
