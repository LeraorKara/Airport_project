package ru.valeria.airport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.valeria.airport.entity.Flight;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Query("SELECT f FROM Flight f WHERE CONCAT(f.flightnumber, ' ', f.departuredatetime, ' ', f.arrivaldatetime, ' ', f.departureairport, ' ', f.arrivalairport) LIKE %?1%")
    List<Flight> search(String keyword);

    @Query("SELECT f FROM Flight f WHERE f.departuredatetime >= :startDate AND f.departuredatetime <= :endDate")
    List<Flight> filterByDate(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    List<Flight> findAllByOrderByDeparturedatetimeAsc();

    List<Flight> findAllByOrderByDeparturedatetimeDesc();

    @Query("SELECT DATE(f.departuredatetime) AS date, COUNT(f) AS count FROM Flight f GROUP BY DATE(f.departuredatetime)")
    List<Map<String, Object>> countFlightsByDate();

    @Query("SELECT f.arrivalairport AS airport, COUNT(f) AS count FROM Flight f GROUP BY f.arrivalairport")
    List<Map<String, Object>> countFlightsByArrivalAirport();

    @Query("SELECT DISTINCT f.arrivalairport FROM Flight f")
    List<String> findAllArrivalAirports();

    List<Flight> findByArrivalairport(String arrivalAirport);
}
