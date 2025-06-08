package com.planeticket.data.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planeticket.data.model.ModelFlight;
import com.planeticket.data.repository.RepositoryFlight;

@RestController
@RequestMapping("/flight")
public class ControllerFlight {
    @Autowired
    private RepositoryFlight rpFlight;

    // daftar semua penerbangan
    @GetMapping("/list")
    public Iterable<ModelFlight> getAllFlight() {
        return rpFlight.findAll();
    }

    // cari penerbangan berdasarkan kota
    @PostMapping("/city")
    public Iterable<ModelFlight> findFlightByCity(@RequestBody ModelFlight flight) {
        String departure = flight.getDeparture();
        String destination = flight.getDestination();
        return rpFlight.findByDepartureAndDestination(departure, destination);
    }

    // add penerbangan
    @PostMapping("/addflight")
    public boolean addFlightList(@RequestBody List<ModelFlight> flights) {
        rpFlight.saveAll(flights);
        return true;
    }

}
