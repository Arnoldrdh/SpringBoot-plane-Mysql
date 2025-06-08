package com.planeticket.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planeticket.data.model.ModelFlight;
import com.planeticket.data.repository.RepositoryFlight;

@Service
public class ServiceFlight {
    @Autowired
    private RepositoryFlight rpFlight;

    // list flight
    public Iterable<ModelFlight> getAllFlight() {
        return rpFlight.findAll();
    }

    // request flight by city
    public Iterable<ModelFlight> findFlightByCity(ModelFlight flight) {
        String departure = flight.getDeparture();
        String destination = flight.getDestination();
        return rpFlight.findByDepartureAndDestination(departure, destination);
    }

    // add penerbangan
    public boolean addFlightList(List<ModelFlight> flights) {
        rpFlight.saveAll(flights);
        return true;
    }
}
