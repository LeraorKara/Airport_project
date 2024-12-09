package ru.valeria.airport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.valeria.airport.entity.Passenger;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    @Query("SELECT p FROM Passenger p WHERE CONCAT(p.firstname, ' ', p.lastname, ' ', p.dateofbirth, ' ', p.contactinfo) LIKE %?1%")
    List<Passenger> search(String keyword);

    @Query("SELECT p FROM Passenger p WHERE p.dateofbirth >= :startDate AND p.dateofbirth <= :endDate")
    List<Passenger> filterByDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    List<Passenger> findAllByOrderByLastnameAsc();

    List<Passenger> findAllByOrderByLastnameDesc();

    @Query("SELECT p.flightid AS flightId, COUNT(p) AS count FROM Passenger p GROUP BY p.flightid")
    List<Map<String, Object>> countPassengersByFlightId();
}
