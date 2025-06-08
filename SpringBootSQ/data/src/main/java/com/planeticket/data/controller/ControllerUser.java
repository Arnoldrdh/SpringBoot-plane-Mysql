package com.planeticket.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planeticket.data.dto.UserSummaryDTO;
import com.planeticket.data.model.ModelUser;
import com.planeticket.data.service.ServiceUser;

@RestController
@RequestMapping("/user")
public class ControllerUser {
    @Autowired
    private ServiceUser srUser;

    // register user baru
    @PostMapping("/register")
    public boolean registerUser(@RequestBody ModelUser User) {
        return srUser.userRegister(User);
    }

    // login user
    @PostMapping("/login")
    public boolean loginUser(@RequestBody ModelUser user) {
        return srUser.userLogin(user);

    }

    // check profile
    @GetMapping("/profile/{userId}")
    public UserSummaryDTO userProfile(@PathVariable Integer userId) {
        return srUser.userProfile(userId);
    }

    // update profile
    @PutMapping("/update/{userId}")
    public boolean updateProfileUser(@PathVariable Integer userId, @RequestBody ModelUser userData) {
        return srUser.updateProfileUser(userId, userData);
    }

    // get all user
    @GetMapping("/all")
    public Iterable<ModelUser> getAllUser() {
        return srUser.getAllUser();
    }

    // delete user
    @DeleteMapping("/delete/{userId}")
    public boolean userDeletebyId(@PathVariable Integer userId) {
        return srUser.userDeletebyId(userId);
    }
}
