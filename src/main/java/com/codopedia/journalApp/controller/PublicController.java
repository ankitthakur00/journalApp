package com.codopedia.journalApp.controller;

import com.codopedia.journalApp.entity.User;
import com.codopedia.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserService userService;

    @GetMapping("/health-check")
    public String healthCheck(){

        return "OK";
    }

    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User createdUser = userService.createUser(user);
        if(createdUser!=null){
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
