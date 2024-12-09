package ru.valeria.airport.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightid;
    private String flightnumber;
    private LocalDateTime departuredatetime;
    private LocalDateTime arrivaldatetime;
    private String departureairport;
    private String arrivalairport;

    // Getters and setters
    public Long getFlightid() {
        return flightid;
    }

    public void setFlightid(Long flightid) {
        this.flightid = flightid;
    }

    public String getFlightnumber() {
        return flightnumber;
    }

    public void setFlightnumber(String flightnumber) {
        this.flightnumber = flightnumber;
    }

    public LocalDateTime getDeparturedatetime() {
        return departuredatetime;
    }

    public void setDeparturedatetime(LocalDateTime departuredatetime) {
        this.departuredatetime = departuredatetime;
    }

    public LocalDateTime getArrivaldatetime() {
        return arrivaldatetime;
    }

    public void setArrivaldatetime(LocalDateTime arrivaldatetime) {
        this.arrivaldatetime = arrivaldatetime;
    }

    public String getDepartureairport() {
        return departureairport;
    }

    public void setDepartureairport(String departureairport) {
        this.departureairport = departureairport;
    }

    public String getArrivalairport() {
        return arrivalairport;
    }

    public void setArrivalairport(String arrivalairport) {
        this.arrivalairport = arrivalairport;
    }
}
