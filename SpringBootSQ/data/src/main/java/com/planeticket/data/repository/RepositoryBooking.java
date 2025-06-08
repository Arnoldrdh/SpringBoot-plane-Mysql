package com.planeticket.data.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.planeticket.data.model.ModelBooking;

public interface RepositoryBooking extends CrudRepository<ModelBooking, Integer> {
    List<ModelBooking> findByUserUserId(Integer userId);
}
