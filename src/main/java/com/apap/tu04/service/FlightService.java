package com.apap.tu04.service;

import com.apap.tu04.model.FlightModel;

public interface FlightService {
    void addFlight(FlightModel flight);
    void deleteFlight(FlightModel flightModel);
    FlightModel getById(Long id);
}
