package com.planeticket.data.repository;

import org.springframework.data.repository.CrudRepository;

import com.planeticket.data.model.ModelPayment;

public interface RepositoryPayment extends CrudRepository<ModelPayment, Integer> {

}
