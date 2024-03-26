package com.lakesideview.hotel.controller;

import com.lakesideview.hotel.exception.UserNotFoundException;
import com.lakesideview.hotel.model.User;
import com.lakesideview.hotel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<User>> getUsers(){
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.FOUND);
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getUsersByEmail(
            @PathVariable("email") String email
    ){
        try{
            User user = userService.getUser(email);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching user");
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUserById(
            @PathVariable("userId") String userId
    ){
        try{
            userService.deleteUserById(userId);
            return ResponseEntity.ok("user deleted successfully");
        } catch (UserNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching user");
        }
    }
}
