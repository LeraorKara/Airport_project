package ru.valeria.airport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.valeria.airport.entity.Flight;
import ru.valeria.airport.repository.FlightRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class FlightService {

    @Autowired
    private FlightRepository repo;

    public List<Flight> listAll(String keyword) {
        if (keyword != null) {
            return repo.search(keyword);
        }
        return repo.findAll();
    }

    public List<Flight> filterByDate(LocalDateTime startDate, LocalDateTime endDate) {
        return repo.filterByDate(startDate, endDate);
    }

    public List<Flight> listAllSorted(String sortOrder) {
        if ("asc".equalsIgnoreCase(sortOrder)) {
            return repo.findAllByOrderByDeparturedatetimeAsc();
        } else if ("desc".equalsIgnoreCase(sortOrder)) {
            return repo.findAllByOrderByDeparturedatetimeDesc();
        }
        return repo.findAll();
    }

    public void save(Flight flight) {
        repo.save(flight);
    }

    public Flight get(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<Map<String, Object>> countFlightsByDate() {
        return repo.countFlightsByDate();
    }

    public List<Map<String, Object>> countFlightsByArrivalAirport() {
        return repo.countFlightsByArrivalAirport();
    }

    public List<String> findAllArrivalAirports() {
        return repo.findAllArrivalAirports();
    }

    public List<Flight> filterByArrivalAirport(String arrivalAirport) {
        return repo.findByArrivalairport(arrivalAirport);
    }

    public List<Flight> listAll() {
        return repo.findAll();
    }
}
