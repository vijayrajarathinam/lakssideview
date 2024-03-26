package com.lakesideview.hotel.controller;

import com.lakesideview.hotel.model.Role;
import com.lakesideview.hotel.model.User;
import com.lakesideview.hotel.service.RoleService;
import com.lakesideview.hotel.service.impl.RoleAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/")
    public ResponseEntity<List<Role>> getAllRoles(){
        return new ResponseEntity<List<Role>>(roleService.getRoles(), HttpStatus.FOUND);
    }

    @PostMapping("/")
    public ResponseEntity<String> createRole(@RequestBody Role role){
        try{
            roleService.createRole(role);
            return ResponseEntity.ok("New role created successfully");
        } catch (RoleAlreadyExistException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{roleId}")
    public void deleteRole(@PathVariable("roleId") Long roleId){
        roleService.deleteRole(roleId);
    }

    @DeleteMapping("/remove-all-users-from-role/{roleId}")
    public Role removeAllUsersFromRole(@PathVariable("roleId") Long roleId){
        return roleService.removeAllUserFromRole(roleId);
    }

    @PostMapping("/remove-user-from-role")
    public User removeUserFromRole(
            @RequestParam("userId") Long userId,
            @RequestParam("roleId") Long roleId
    ){
        return roleService.removeUserFromRole(userId,roleId);
    }

    @PostMapping("/assign-user-to-role")
    public User assignUserToRole(
            @RequestParam("userId") Long userId,
            @RequestParam("roleId") Long roleId
    ){
        return roleService.assignRoleToUser(userId,roleId);
    }


}
