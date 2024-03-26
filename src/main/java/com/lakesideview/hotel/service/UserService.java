package com.lakesideview.hotel.service;


import com.lakesideview.hotel.model.User;
import jakarta.transaction.Transactional;

import java.util.List;

public interface UserService {

    User register(User user);

    List<User> getUsers();

    void deleteUser(String email);

    User getUser(String email);

    @Transactional
    void deleteUserById(String id);
}
