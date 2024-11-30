package com.codopedia.journalApp.controller;

import com.codopedia.journalApp.entity.User;
import com.codopedia.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> allUsers = userService.getAllUsers();
        if(allUsers!=null && !allUsers.isEmpty()){
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/create-admin-user")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<User> createAdminUser(@RequestBody User user){
        User createdUser = userService.createAdminUser(user);
        if(createdUser!=null)
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/create-super-admin")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<User>createSuperAdmin(@RequestBody User user){
        User createdUser = userService.createSuperAdminUser(user);
        if(createdUser!=null)
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
