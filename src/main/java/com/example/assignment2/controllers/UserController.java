package com.example.assignment2.controllers;

import com.example.assignment2.CustomizedResponse;
import com.example.assignment2.models.UserModel;
import com.example.assignment2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@CrossOrigin(origins = "https://chill-app-frontend.herokuapp.com")
@RestController
public class UserController {

    @Autowired //dependency injection
    private UserService service;

    @GetMapping("/user")
    public ResponseEntity getUsers()
    {
        CustomizedResponse response = new CustomizedResponse("A list of all users", service.getUsers());
        return new ResponseEntity(response, HttpStatus.OK);
    }

    //return a user based on ID
    @GetMapping("/user/{id}")
    public ResponseEntity getUser(@PathVariable("id") String id){
        CustomizedResponse customizedResponse = null;
        try {
            customizedResponse = new CustomizedResponse("User with id " + id, Collections.singletonList(service.getUser(id)));
        } catch (Exception e) {
            customizedResponse = new CustomizedResponse(e.getMessage(), null);
            return new ResponseEntity(customizedResponse, HttpStatus.NOT_FOUND); //Error 404
        }
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }

    //create a user
    @PostMapping(value="/user", consumes = {   //consumes is when data comes in from request body
            MediaType.APPLICATION_JSON_VALUE
    })

    public ResponseEntity addUser(@RequestBody UserModel user)
    {
        CustomizedResponse customizedResponse = null;
        try {
            customizedResponse = new CustomizedResponse("Added user successfully" , Collections.singletonList(service.addUser(user)));
        } catch (Exception e) {
            customizedResponse = new CustomizedResponse(e.getMessage(), null);
            return new ResponseEntity(customizedResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }
}
