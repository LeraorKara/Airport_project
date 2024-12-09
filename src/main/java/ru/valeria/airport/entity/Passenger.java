package ru.valeria.airport.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passengerid;
    private String firstname;
    private String lastname;
    private LocalDate dateofbirth;
    private String contactinfo;
    private Long flightid;

    // Getters and setters
    public Long getPassengerid() {
        return passengerid;
    }

    public void setPassengerid(Long passengerid) {
        this.passengerid = passengerid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(LocalDate dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getContactinfo() {
        return contactinfo;
    }

    public void setContactinfo(String contactinfo) {
        this.contactinfo = contactinfo;
    }

    public Long getFlightid() {
        return flightid;
    }

    public void setFlightid(Long flightid) {
        this.flightid = flightid;
    }
}
