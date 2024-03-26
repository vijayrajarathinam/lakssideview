package com.lakesideview.hotel.service.impl;

import com.lakesideview.hotel.exception.UserNotFoundException;
import com.lakesideview.hotel.model.Role;
import com.lakesideview.hotel.model.User;
import com.lakesideview.hotel.repository.RoleRepository;
import com.lakesideview.hotel.repository.UserRepository;
import com.lakesideview.hotel.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role createRole(Role role) {
        String roleName = "ROLE_"+role.getName().toUpperCase();
        Role newRole = new Role(roleName);
        if(roleRepository.existsByName(role.getName()))
            throw new RoleAlreadyExistException(role.getName()+ " role already exist");
        return roleRepository.save(newRole);
    }

    @Override
    public void deleteRole(Long id) {
        this.removeAllUserFromRole(id);
        roleRepository.deleteById(id);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name).get();
    }

    @Override
    public User removeUserFromRole(Long userId, Long roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);
        if(role.isPresent() && role.get().getUsers().contains(user.get())){
            role.get().removeUserFromRole(user.get());
            roleRepository.save(role.get());
            return user.get();
        }
        throw new UserNotFoundException("User not found");
    }

    @Override
    public User assignRoleToUser(Long userId, Long roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);
        if(user.isPresent() && user.get().getRoles().contains(role.get()))
            throw new RoleAlreadyExistException(
                    user.get().getFirstName()+" is already assigned to the "+ role.get().getName()
            );

        if(role.isPresent()){
            role.get().assignRoleToUser(user.get());
            roleRepository.save(role.get());
        }
        return user.get();
    }

    @Override
    public Role removeAllUserFromRole(Long roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        role.get().removeAllUsersFromRole();

        return roleRepository.save(role.get());
    }
}
