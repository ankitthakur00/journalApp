package com.codopedia.journalApp.controller;

import com.codopedia.journalApp.entity.User;
import com.codopedia.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

//    @GetMapping
//    public ResponseEntity<List<User>> getAllUser(){
//        List<User> users = userService.getAllUsers();
//        if(users!=null){
//            return new ResponseEntity<>(users, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

//    @GetMapping("/{id}")
//    public  ResponseEntity<User> getUserById(@PathVariable ObjectId id){
//        User user = userService.getUserById(id);
//        if(user!=null){
//            return new ResponseEntity<>(user, HttpStatus.OK);
//        }
//        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

//    @PostMapping
//    public ResponseEntity<User> createUser(@RequestBody User user){
//        User createdUser = userService.createUser(user);
//        if(createdUser!=null){
//            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
//        }
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }

    @PutMapping
    public ResponseEntity<User> updateUserById(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User updatedUser = userService.updateUser(user, authentication.getName());
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.deleteUserByName(authentication.getName());
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
