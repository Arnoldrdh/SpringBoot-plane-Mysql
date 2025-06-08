package com.planeticket.data.repository;

import org.springframework.data.repository.CrudRepository;

import com.planeticket.data.model.ModelUser;

public interface RepositoryUser extends CrudRepository<ModelUser, Integer> {

    ModelUser findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
}
