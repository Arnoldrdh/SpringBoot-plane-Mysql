package com.planeticket.data.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.planeticket.data.dto.UserSummaryDTO;
import com.planeticket.data.model.ModelUser;
import com.planeticket.data.repository.RepositoryUser;

@Service
public class ServiceUser {
    @Autowired
    private RepositoryUser rpUser;

    // register user
    public boolean userRegister(ModelUser User) {
        if (rpUser.existsByUsername(User.getUsername())) {
            return false; // username sudah digunakan
        }

        if (rpUser.existsByEmail(User.getEmail())) {
            return false; // email sudah digunakan
        }

        if (rpUser.existsByPhoneNumber(User.getPhoneNumber())) {
            return false;
        }

        rpUser.save(User);
        return true; // berhasil
    }

    // login user
    public boolean userLogin(ModelUser user) {
        ModelUser userData = rpUser.findByUsername(user.getUsername());

        return userData != null && userData.getPassword().equals(user.getPassword());

    }

    // profile user
    public UserSummaryDTO userProfile(Integer userId) {
        Optional<ModelUser> userOptional = rpUser.findById(userId);
        if (userOptional.isPresent()) {
            ModelUser userProfile = userOptional.get();
            UserSummaryDTO dto = new UserSummaryDTO();
            dto.setUsername(userProfile.getUsername());
            dto.setEmail(userProfile.getEmail());
            dto.setPhoneNumber(userProfile.getPhoneNumber());

            return dto;
        }
        return null;
    }

    // update profile
    public boolean updateProfileUser(Integer userId, ModelUser userData) {
        if (rpUser.existsById(userId)) {
            @SuppressWarnings("Null")
            ModelUser user = rpUser.findById(userId).get();
            user.setUsername(userData.getUsername());
            user.setEmail(userData.getEmail());
            user.setPassword(userData.getPassword());
            user.setPhoneNumber(userData.getPhoneNumber());
            rpUser.save(user);
            return true;
        }
        return false;
    }

    // get all user
    public Iterable<ModelUser> getAllUser() {
        return rpUser.findAll();
    }

    // delete user
    public boolean userDeletebyId(@PathVariable Integer userId) {
        if (rpUser.existsById(userId)) {
            rpUser.deleteById(userId);
            return true;
        }

        return false;
    }
}
