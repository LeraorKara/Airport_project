package ru.valeria.airport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ru.valeria.airport.entity.Flight;
import ru.valeria.airport.entity.Passenger;
import ru.valeria.airport.service.CustomUserDetailsService;
import ru.valeria.airport.service.FlightService;
import ru.valeria.airport.service.PassengerService;

@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ApiController {
    @Autowired
    private FlightService flightService;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping(value = "/flights", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<Flight> saveFlight(Flight flight) {
        if (flight.getFlightnumber() == null || flight.getDeparturedatetime() == null ||
                flight.getArrivaldatetime() == null || flight.getDepartureairport() == null ||
                flight.getArrivalairport() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        flightService.save(flight);
        return new ResponseEntity<>(flight, HttpStatus.CREATED);
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<Flight> getFlight(@PathVariable Long id) {
        Flight flight = flightService.get(id);
        if (flight == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(flight, HttpStatus.OK);
    }

    @PatchMapping("/flights/{id}")
    public ResponseEntity<Flight> patchFlight(@PathVariable Long id, @RequestBody Flight flight) {
        Flight existingFlight = flightService.get(id);
        if (existingFlight == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (flight.getFlightnumber() != null) {
            existingFlight.setFlightnumber(flight.getFlightnumber());
        }
        if (flight.getDeparturedatetime() != null) {
            existingFlight.setDeparturedatetime(flight.getDeparturedatetime());
        }
        if (flight.getArrivaldatetime() != null) {
            existingFlight.setArrivaldatetime(flight.getArrivaldatetime());
        }
        if (flight.getDepartureairport() != null) {
            existingFlight.setDepartureairport(flight.getDepartureairport());
        }
        if (flight.getArrivalairport() != null) {
            existingFlight.setArrivalairport(flight.getArrivalairport());
        }
        flightService.save(existingFlight);
        return new ResponseEntity<>(existingFlight, HttpStatus.OK);
    }

    @DeleteMapping("/flights/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/passengers", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<Passenger> savePassenger(Passenger passenger) {
        passengerService.save(passenger);
        return new ResponseEntity<>(passenger, HttpStatus.CREATED);
    }

    @GetMapping("/passengers/{id}")
    public ResponseEntity<Passenger> getPassenger(@PathVariable Long id) {
        Passenger passenger = passengerService.get(id);
        if (passenger == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(passenger, HttpStatus.OK);
    }

    @PatchMapping("/passengers/{id}")
    public ResponseEntity<Passenger> patchPassenger(@PathVariable Long id, @RequestBody Passenger passenger) {
        Passenger existingPassenger = passengerService.get(id);
        if (existingPassenger == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (passenger.getFirstname() != null) {
            existingPassenger.setFirstname(passenger.getFirstname());
        }
        if (passenger.getLastname() != null) {
            existingPassenger.setLastname(passenger.getLastname());
        }
        if (passenger.getFlightid() != null) {
            existingPassenger.setFlightid(passenger.getFlightid());
        }
        if (passenger.getContactinfo() != null) {
            existingPassenger.setContactinfo(passenger.getContactinfo());
        }

        if (passenger.getDateofbirth() != null) {
            existingPassenger.setDateofbirth(passenger.getDateofbirth());
        }

        passengerService.save(existingPassenger);
        return new ResponseEntity<>(existingPassenger, HttpStatus.OK);
    }

    @DeleteMapping("/passengers/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        passengerService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}