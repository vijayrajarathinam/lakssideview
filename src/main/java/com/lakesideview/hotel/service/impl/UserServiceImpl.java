package com.lakesideview.hotel.service.impl;


import com.lakesideview.hotel.exception.UserAlreadyExistsException;
import com.lakesideview.hotel.exception.UserNotFoundException;
import com.lakesideview.hotel.model.Role;
import com.lakesideview.hotel.model.User;
import com.lakesideview.hotel.repository.RoleRepository;
import com.lakesideview.hotel.repository.UserRepository;
import com.lakesideview.hotel.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {
        if(userRepository.existByEmail(user.getEmail()))
            throw new UserAlreadyExistsException("user with email"+ user.getEmail() +" already exist");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        user.setRoles(Collections.singletonList(userRole));
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(String email) {
        User user = getUser(email);
        if(user != null)
            userRepository.deleteByEmail(email);
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User Not Found")
        );
    }

    @Transactional
    @Override
    public void deleteUserById(String id){

    }
}
